package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.dto.RegisterClientDto;
import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.repository.ClientRepository;
import com.ebusiness.ebusiness.service.service.ClientService;
import com.ebusiness.ebusiness.service.service.RoleService;
import com.ebusiness.ebusiness.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RoleService roleService, PasswordEncoder passwordEncoder, UserService userService) {
        this.clientRepository = clientRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client registerClient(RegisterClientDto registerClientDto) {
        if (userService.existsByEmail(registerClientDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userService.existsByUsername(registerClientDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        Client client = new Client();
        client.setUsername(registerClientDto.getUsername());
        client.setEmail(registerClientDto.getEmail());
        client.setPassword(passwordEncoder.encode(registerClientDto.getPassword()));
        client.setPhone(registerClientDto.getPhone());
        client.setAddress(registerClientDto.getAddress());
        client.setRegistrationDate(LocalDateTime.now());

        Role role = roleService.getRoleByName("CLIENT")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        client.setRoles(Collections.singletonList(role));

        return createClient(client);
    }

    @Override
    public Client updateClient(Integer id, Client updatedClient) {
        return clientRepository.findById(id)
                .map(existing -> {
                    existing.setUsername(updatedClient.getUsername());
                    existing.setEmail(updatedClient.getEmail());
                    existing.setPassword(updatedClient.getPassword());
                    existing.setPhone(updatedClient.getPhone());
                    existing.setAddress(updatedClient.getAddress());
                    existing.setRegistrationDate(updatedClient.getRegistrationDate());
                    return clientRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Client not found"));
    }

    @Override
    public void deleteClient(Integer id) {
        clientRepository.deleteById(id);
    }
}
