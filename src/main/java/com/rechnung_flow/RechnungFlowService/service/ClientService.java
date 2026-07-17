package com.rechnung_flow.RechnungFlowService.service;


import com.rechnung_flow.RechnungFlowService.model.enteties.Client;
import com.rechnung_flow.RechnungFlowService.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id){
        return clientRepository.findById(id);
    }

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient){
        Client client = clientRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Client not found"));

        client.setCompanyName(updatedClient.getCompanyName());
        client.setContactPerson(updatedClient.getContactPerson());
        client.setEmail(updatedClient.getEmail());
        client.setPhone(updatedClient.getPhone());

        return clientRepository.save(client);
    }

    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }
}
