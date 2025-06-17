package com.ebusiness.ebusiness.service.impl;

import com.ebusiness.ebusiness.entity.Client;
import com.ebusiness.ebusiness.repository.ClientRepository;
import com.ebusiness.ebusiness.service.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
