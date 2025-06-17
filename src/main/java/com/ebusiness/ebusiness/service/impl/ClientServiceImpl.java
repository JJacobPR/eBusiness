package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.dto.RegisterClientDto;
import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.entity.Role;
import com.ebusiness.ebusiness.repository.ClientRepository;
import com.ebusiness.ebusiness.service.service.ClientService;
import com.ebusiness.ebusiness.service.service.RoleService;
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

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
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
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client registerClient(RegisterClientDto dto) {
        if (clientRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        Client client = new Client();
        client.setUsername(dto.getUsername());
        client.setEmail(dto.getEmail());
        client.setPassword(passwordEncoder.encode(dto.getPassword()));
        client.setPhone(dto.getPhone());
        client.setAddress(dto.getAddress());
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
