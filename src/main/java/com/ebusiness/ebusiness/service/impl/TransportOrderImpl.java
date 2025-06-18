package com.ebusiness.ebusiness.service.impl;

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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        Driver driver = driverRepository.findById(transportOrderCreateDto.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        TransportOrder order = new TransportOrder();
        order.setClient(client);
        order.setDriver(driver);
        order.setOriginAddress(transportOrderCreateDto.getOriginAddress());
        order.setDestinationAddress(transportOrderCreateDto.getDestinationAddress());

        //Add define different statuses
        //Add Transport order modification
        order.setPrice(calculateCost(transportOrderCreateDto.getPackages()));
        order.setStatus("NEW");
        order.setCreatedAt(LocalDateTime.now());

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
            order.setQrCode(updatedTransportOrder.getQrCode());
            order.setPackages(updatedTransportOrder.getPackages());
            return transportOrderRepository.save(order);
        }).orElseGet(() -> {
            updatedTransportOrder.setOrderID(id);
            return transportOrderRepository.save(updatedTransportOrder);
        });
    }

    @Override
    public double calculateCost(List<PackageCreateDto> packages) {
        double totalCost = 0.0;

        for (PackageCreateDto pkg : packages) {

            double weightKg = pkg.getWeight() / 1000.0;

            double volumeCm3 = pkg.getHeight() * pkg.getWidth() * pkg.getDepth();

            double volumetricWeightKg = volumeCm3 / 6000.0;

            double billableWeight = Math.max(weightKg, volumetricWeightKg);

            double cost = billableWeight * 10.0;

            if (Boolean.TRUE.equals(pkg.isFragile())) {
                cost += 3.0; // flat fragile surcharge
            }

            totalCost += cost;
        }

        return Math.round(totalCost * 100.0) / 100.0; // Round to 2 decimals
    }

    @Override
    public void deleteTransportOrder(Integer id) {
        transportOrderRepository.deleteById(id);
    }
}
