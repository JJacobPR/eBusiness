package com.ebusiness.ebusiness.service.service;

import com.ebusiness.ebusiness.dto.RegisterClientDto;
import com.ebusiness.ebusiness.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAllClients();

    Optional<Client> getClientById(Integer id);

    Optional<Client> getClientByEmail(String email);

    boolean existsByEmail(String email);

    Client createClient(Client client);

    Client registerClient(RegisterClientDto registerClientDto);

    Client updateClient(Integer id, Client updatedClient);

    void deleteClient(Integer id);
}
