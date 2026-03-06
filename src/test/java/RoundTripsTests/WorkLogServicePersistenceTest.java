package RoundTripsTests;

import de.rechnungflow.model.WorkLog;
import de.rechnungflow.service.WorkLogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkLogServicePersistenceTest {
        @TempDir
        Path tempDir;

        @Test
        void saveAndLoad_roundtrip_keepsDataAndNextId() throws IOException {
            //GIVEN
            Path file = tempDir.resolve("worklogs.json");
            WorkLogService s1 = new WorkLogService(file);

            WorkLog wl1 = new WorkLog(1, 2, 1, LocalDate.of(2026, 3, 2),
                    new BigDecimal("8"), "cleaning", true);

            WorkLog wl2 = new WorkLog(2, 1, 1, LocalDate.of(2026, 3, 4),
                    new BigDecimal("10"), "floor", true);

            s1.add(wl1);
            s1.add(wl2);

            //WHEN save -> new instance -> load
            s1.saveToFile();

            System.out.println("Temp file: " + file.toAbsolutePath());
            System.out.println("Exists after save: " + Files.exists(file));
            System.out.println("Size after save: " + (Files.exists(file) ? Files.size(file) : -1));
            if (Files.exists(file)) {
                System.out.println("Content:\n" + Files.readString(file));
            }

            WorkLogService s2 = new WorkLogService(file);

            //THEN
            assertEquals(2, s2.getAll().size(), "The number of Worklogs must remain the same");
            WorkLog loaded1 = s2.findById(1).orElseThrow();
            assertEquals("cleaning", loaded1.getDescription());
            assertTrue(loaded1.getApprove());

            WorkLog loaded2 = s2.findById(2).orElseThrow();
            assertEquals("floor", loaded2.getDescription());
            assertTrue(loaded2.getApprove());

            //nextId test
            WorkLog created = s2.create(10, 1, LocalDate.of(2026, 3, 5),
                    new BigDecimal("10"), "windows", true);

            assertEquals(3, created.getId(), "next Id must be correct (max)+1");

        }
}
