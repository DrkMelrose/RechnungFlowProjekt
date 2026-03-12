package RoundTripsTests;

import de.rechnungflow.model.CleaningObject;
import de.rechnungflow.service.CleaningObjectService;
import de.rechnungflow.service.WorkLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CleaningObjectServicePersistenceTest {
    @TempDir
    Path tempDir;

    @Test
    void saveAndLoad_roundtrip_cleaningObjectService() throws Exception{
        //GIVEN
        Path file = tempDir.resolve("cleaningObject.json");

        CleaningObjectService objectService = new CleaningObjectService(file);

        CleaningObject obj1 = new CleaningObject(1, 1, "MüllGmbh", "kölner str. 555", new BigDecimal("30"), new BigDecimal("1000"), true);
        CleaningObject obj2 = new CleaningObject(2, 2, "GipsyClub", "Berliner str.8", new BigDecimal("50"), new BigDecimal("1500"), true);

        objectService.add(obj1);
        objectService.add(obj2);

        //WHEN
        objectService.saveToFile();

        if (Files.exists(file)){
            System.out.println("Content: " + Files.readString(file));
        }

        CleaningObjectService objectService2 = new CleaningObjectService(file);

        //THEN
        assertEquals(2, objectService2.getAll().size(), "The number of cleaning objects must remain the same");
        CleaningObject loaded1 = objectService2.findCleaningObjectById(1).orElseThrow();
        assertEquals("MüllGmbh", loaded1.getName());
        assertTrue(loaded1.getActive());



    }
}
