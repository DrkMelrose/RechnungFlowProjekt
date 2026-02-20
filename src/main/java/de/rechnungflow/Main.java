package de.rechnungflow;

import de.rechnungflow.cli.CliApp;
import de.rechnungflow.model.*;
import de.rechnungflow.service.InvoiceService;

public class Main {
    static void main(String[] args) {

        WorkLogService workLogService = new WorkLogService();
        CleaningObjectService cleaningObjectService = new CleaningObjectService();
        ClientService clientService = new ClientService();
        InvoiceService invoiceService = new InvoiceService();
        EmployeeService employeeService = new EmployeeService();

        InvoiceGeneratorService invoiceGeneratorService = new InvoiceGeneratorService(
                workLogService,
                cleaningObjectService,
                clientService,
                invoiceService
        );

        clientService.loadFromFile();
        employeeService.loadFromFile();
        cleaningObjectService.loadFromFile();

        CliApp app = new CliApp(
                invoiceGeneratorService,
                invoiceService,
                workLogService,
                cleaningObjectService,
                clientService,
                employeeService
        );

        app.run();
    }
}
