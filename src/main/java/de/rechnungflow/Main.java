package de.rechnungflow;

import de.rechnungflow.cli.CliApp;
import de.rechnungflow.model.CleaningObjectService;
import de.rechnungflow.model.ClientService;
import de.rechnungflow.model.InvoiceGeneratorService;
import de.rechnungflow.model.WorkLogService;
import de.rechnungflow.service.InvoiceService;

public class Main {
    static void main(String[] args) {

        WorkLogService workLogService = new WorkLogService();
        CleaningObjectService cleaningObjectService = new CleaningObjectService();
        ClientService clientService = new ClientService();
        InvoiceService invoiceService = new InvoiceService();

        InvoiceGeneratorService invoiceGeneratorService = new InvoiceGeneratorService(
                workLogService,
                cleaningObjectService,
                clientService,
                invoiceService
        );

        CliApp app = new CliApp(
                invoiceGeneratorService,
                invoiceService,
                workLogService,
                cleaningObjectService,
                clientService
        );

        app.run();
    }
}
