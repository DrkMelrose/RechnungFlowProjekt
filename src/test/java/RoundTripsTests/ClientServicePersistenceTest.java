package RoundTripsTests;

import de.rechnungflow.model.Client;
import de.rechnungflow.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientServicePersistenceTest {
    @TempDir
    Path tempDir;

    @Test
    public void saveUndLoad_roadtrip_clientService(){
        //GIVEN
        Path file = tempDir.resolve("clients.json");

        ClientService clientService = new ClientService(file);

        Client client = new Client(1, "OpanaGmbh","Olaf Scholz","olafsexy@gmail.com", "12377127");

        clientService.add(client);

        //WHEN
        clientService.saveToFile();

        ClientService clientService2 = new ClientService(file);

        //THEN
        assertEquals(1, clientService2.getAll().size());
        assertEquals("OpanaGmbh", clientService2.findClientById(1).getCompanyName());
        assertNotNull(clientService2);

        //test nextID
        Client created = clientService2.createClient("OpanananaGmbH", "Habibi Müller", "hihihi@gmail.com", "127837127");
        assertEquals(2, created.getId());
    }
}
