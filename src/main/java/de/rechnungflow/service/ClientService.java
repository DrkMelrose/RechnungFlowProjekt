package de.rechnungflow.service;

import com.fasterxml.jackson.core.type.TypeReference;
import de.rechnungflow.model.Client;
import de.rechnungflow.persistance.JsonStorage;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final Path FILE_PATH = Path.of("data", "clients.json");
    private final JsonStorage storage = new JsonStorage();

    private int nextId = 1;
    private final List<Client> clients = new ArrayList<>();

    public void loadFromFile(){
        List<Client> loaded = storage.readList(FILE_PATH, new TypeReference<>() {});
        clients.clear();
        clients.addAll(loaded);

        nextId = clients.stream()
                .mapToInt(Client::getId)
                .max()
                .orElse(0) + 1;
    }

    public void saveToFile(){
        storage.writeList(FILE_PATH, clients);
    }

    public Client createClient(String companyName, String contactPerson, String email, String phone){
        Client client = new Client(nextId++, companyName, contactPerson, email, phone);
        clients.add(client);
        saveToFile();
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
