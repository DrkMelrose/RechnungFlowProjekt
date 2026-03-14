import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.WorkLog;
import de.rechnungflow.service.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DefensiveTestForCreationWorkLogsAndGenerationInvoices {
    @Test
    void createWorkLog_shouldRejectZeroHours(){
        //GIVEN
        WorkLogService workLogService = new WorkLogService();

        int employeeId = 1;
        int objectId = 1;

        //WHEN
        WorkLog result = workLogService.createWorkLog(
                employeeId,
                objectId,
                LocalDate.now(),
                new BigDecimal("0"),
                "something",
                true
        );

        //THEN
        assertNull(result);
    }

    @Test
    void createWorkLog_shouldRejectNegetiveHours(){
        //GIVEN
        WorkLogService workLogService = new WorkLogService();

        int employeeId = 1;
        int objectId = 1;

        //WHEN
        WorkLog result = workLogService.createWorkLog(
                employeeId,
                objectId,
                LocalDate.now(),
                new BigDecimal("-7"),
                "somebody",
                true
        );

        //THEN
        assertNull(result);
    }

    @Test
    void generateInvoice_shouldReturnNullWhenObjectNotFound(){
        //GIVEN
        WorkLogService workLogService = new WorkLogService();
        CleaningObjectService cleaningObjectService = new CleaningObjectService();
        ClientService clientService = new ClientService();
        InvoiceService invoiceService = new InvoiceService();


        InvoiceGeneratorService invoiceGeneratorService =
                new InvoiceGeneratorService(
                        workLogService,
                        cleaningObjectService,
                        clientService,
                        invoiceService
                );


        int nonExistingObject = 999;

        //WHEN

        Invoice invoice = invoiceGeneratorService.generateInvoiceForObject(
                nonExistingObject,
                LocalDate.now().minusDays(7),
                LocalDate.now()
        );

        //THEN
        assertNull(invoice);


    }
}
