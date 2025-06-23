package com.ebusiness.ebusiness.rest;


import com.ebusiness.ebusiness.dto.ClientResponseDto;
import com.ebusiness.ebusiness.dto.DriverResponseDto;
import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.entity.Driver;
import com.ebusiness.ebusiness.entity.UserEntity;
import com.ebusiness.ebusiness.service.service.ClientService;
import com.ebusiness.ebusiness.service.service.DriverService;
import com.ebusiness.ebusiness.service.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class UserController {

    private final ClientService clientService;
    private final DriverService driverService;

    public UserController(ClientService clientService, DriverService driverService) {
        this.clientService = clientService;
        this.driverService = driverService;
    }

    @GetMapping("/admin/clients")
    public ResponseEntity<List<ClientResponseDto>> getAllClients() {
        List<ClientResponseDto> response = clientService.getAllClients().stream()
                .map(this::mapToClientDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/drivers")
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        List<DriverResponseDto> response = driverService.getAllDrivers().stream()
                .map(this::mapToDriverDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Integer clientId) {
        Client client = clientService.getClientById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
        return ResponseEntity.ok(mapToClientDto(client));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable Integer driverId) {
        Driver driver = driverService.getDriverById(driverId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found"));
        return ResponseEntity.ok(mapToDriverDto(driver));
    }


    private ClientResponseDto mapToClientDto(Client client) {
        ClientResponseDto dto = new ClientResponseDto();
        dto.setUserID(client.getUserID());
        dto.setUsername(client.getUsername());
        dto.setEmail(client.getEmail());
        dto.setRegistration_date(client.getRegistrationDate());
        dto.setRole("CLIENT");
        dto.setPhone(client.getPhone());
        dto.setAddress(client.getAddress());
        return dto;
    }

    private DriverResponseDto mapToDriverDto(Driver driver) {
        DriverResponseDto dto = new DriverResponseDto();
        dto.setUserID(driver.getUserID());
        dto.setUsername(driver.getUsername());
        dto.setEmail(driver.getEmail());
        dto.setRegistration_date(driver.getRegistrationDate());
        dto.setRole("DRIVER");
        dto.setPhone(driver.getPhone());
        dto.setVehicleDetails(driver.getVehicleDetails());
        dto.setCity(driver.getCity());
        dto.setAvailabilityStatus(driver.getAvailabilityStatus());
        dto.setVerificationStatus(driver.getVerificationStatus());
        dto.setBlocked(driver.getBlocked());
        return dto;
    }
}