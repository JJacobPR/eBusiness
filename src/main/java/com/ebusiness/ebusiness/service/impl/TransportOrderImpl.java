package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.config.TransportOrderStatus;
import com.ebusiness.ebusiness.dto.PackageCreateDto;
import com.ebusiness.ebusiness.dto.TransportOrderCreateDto;
import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.entity.Package;
import com.ebusiness.ebusiness.entity.TransportOrder;
import com.ebusiness.ebusiness.repository.ClientRepository;
import com.ebusiness.ebusiness.repository.DriverRepository;
import com.ebusiness.ebusiness.repository.PackageRepository;
import com.ebusiness.ebusiness.repository.TransportOrderRepository;
import com.ebusiness.ebusiness.service.service.TransportOrderService;
import com.ebusiness.ebusiness.utils.QrCodeGenerator;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class TransportOrderImpl implements TransportOrderService {


    private final TransportOrderRepository transportOrderRepository;
    private final PackageRepository packageRepository;
    private final ClientRepository clientRepository;
    private final DriverRepository driverRepository;

    public TransportOrderImpl(TransportOrderRepository transportOrderRepository,
                                     PackageRepository packageRepository,
                                     ClientRepository clientRepository,
                                     DriverRepository driverRepository) {
        this.transportOrderRepository = transportOrderRepository;
        this.packageRepository = packageRepository;
        this.clientRepository = clientRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    public List<TransportOrder> getAllTransportOrders() {
        return transportOrderRepository.findAll();
    }

    @Override
    public Optional<TransportOrder> getTransportOrderById(Integer id) {
        return transportOrderRepository.findById(id);
    }

    @Override
    public TransportOrder createTransportOrder(TransportOrder transportOrder) {
        return transportOrderRepository.save(transportOrder);
    }

    @Override
    public TransportOrder createTransportOrder(String email, TransportOrderCreateDto transportOrderCreateDto) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Driver driver = driverRepository.findById(findAvailableDriver(transportOrderCreateDto.getOriginAddress(), transportOrderCreateDto.getDestinationAddress()))
                .orElseThrow(() -> new RuntimeException("No available driver found"));;

        TransportOrder order = new TransportOrder();
        order.setClient(client);
        order.setOriginAddress(transportOrderCreateDto.getOriginAddress());
        order.setDestinationAddress(transportOrderCreateDto.getDestinationAddress());
        order.setPickupTime(transportOrderCreateDto.getPickupTime());
        order.setDeliveryTime(transportOrderCreateDto.getUnloadTime());
        order.setHelpUnload(transportOrderCreateDto.isHelpUnload());

        //Add Transport order modification
        order.setPrice(calculateCost(transportOrderCreateDto.getPackages(), transportOrderCreateDto.isHelpUnload()));
        order.setStatus(TransportOrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());


        order.setDriver(driver);
        driver.setAvailabilityStatus(false);
        driverRepository.save(driver);

        TransportOrder savedOrder = transportOrderRepository.save(order);

        List<Package> packages = transportOrderCreateDto.getPackages().stream().map(pkgDto -> {
            Package pkg = new Package();
            pkg.setTransportOrder(savedOrder);
            pkg.setHeight(pkgDto.getHeight());
            pkg.setWidth(pkgDto.getWidth());
            pkg.setDepth(pkgDto.getDepth());
            pkg.setWeight(pkgDto.getWeight());
            pkg.setFragile(pkgDto.isFragile());
            pkg.setComment(pkgDto.getComment());
            pkg.setCreatedAt(LocalDateTime.now());
            return pkg;
        }).toList();

        packageRepository.saveAll(packages);

        savedOrder.setPackages(packages);

        return savedOrder;
    }

    @Override
    public TransportOrder updateTransportOrder(Integer id, TransportOrder updatedTransportOrder) {
        return transportOrderRepository.findById(id).map(order -> {
            order.setClient(updatedTransportOrder.getClient());
            order.setDriver(updatedTransportOrder.getDriver());
            order.setOriginAddress(updatedTransportOrder.getOriginAddress());
            order.setDestinationAddress(updatedTransportOrder.getDestinationAddress());
            order.setPrice(updatedTransportOrder.getPrice());
            order.setStatus(updatedTransportOrder.getStatus());
            order.setCreatedAt(updatedTransportOrder.getCreatedAt());
            order.setPickupTime(updatedTransportOrder.getPickupTime());
            order.setDeliveryTime(updatedTransportOrder.getDeliveryTime());
            order.setPackages(updatedTransportOrder.getPackages());
            return transportOrderRepository.save(order);
        }).orElseGet(() -> {
            updatedTransportOrder.setOrderID(id);
            return transportOrderRepository.save(updatedTransportOrder);
        });
    }

    @Override
    public TransportOrder updateTransportOrderStatus(Integer id, TransportOrderStatus newStatus) {
        return transportOrderRepository.findById(id).map(order -> {
            if (order.getStatus() == TransportOrderStatus.DELIVERED) {
                throw new IllegalArgumentException("Cannot set status after it has been DELIVERED");
            }
            order.setStatus(newStatus);
            return transportOrderRepository.save(order);
        }).orElseThrow(() -> new IllegalArgumentException("TransportOrder not found with id: " + id));
    }


    @Override
    public String createQRCode(Integer id) {
        try {
            BufferedImage qrImage = QrCodeGenerator.generateQRCodeImage(id.toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR code");
        }
    }

    @Override
    public double calculateCost(List<PackageCreateDto> packages, boolean helpUnload) {
        double totalCost = 10.0; // Base cost

        for (PackageCreateDto pkg : packages) {
            double weightKg = pkg.getWeight() / 1000.0;
            double volumeCm3 = pkg.getHeight() * pkg.getWidth() * pkg.getDepth();
            double volumetricWeightKg = volumeCm3 / 6000.0;

            double billableWeight = Math.max(weightKg, volumetricWeightKg);
            double cost = billableWeight * 10.0;

            if (Boolean.TRUE.equals(pkg.isFragile())) {
                cost += 3.0; // Flat fragile surcharge
            }

            totalCost += cost;
        }

        if (helpUnload) {
            totalCost = totalCost + packages.size() * 5.0;
        }

        return Math.round(totalCost * 100.0) / 100.0; // Round to 2 decimal places
    }


    @Override
    public void deleteTransportOrder(Integer id) {
        transportOrderRepository.deleteById(id);
    }

    public Integer findAvailableDriver(String originAddress, String destinationAddress) {
        return driverRepository.findAll().stream()
                .filter(Driver::getVerificationStatus)
                .filter(driver -> !driver.getBlocked())
                .filter(Driver::getAvailabilityStatus)
                .filter(driver -> {
                    String city = driver.getCity().toLowerCase();
                    return originAddress.toLowerCase().contains(city) ||
                            destinationAddress.toLowerCase().contains(city);
                })
                .map(Driver::getUserID)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No available driver found for the provided addresses."));
    }

}
