package de.rechnungflow.model;

import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private int nextId = 1;
    private final List<Client> clients = new ArrayList<>();

    public Client createClient(String companyName, String contactPerson, String email, String phone){
        Client client = new Client(nextId++, companyName, contactPerson, email, phone);
        clients.add(client);
        return client;
    }

    public Client findClientById(int id){
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Client> getAll(){
        return new ArrayList<>(clients);
    }
}
