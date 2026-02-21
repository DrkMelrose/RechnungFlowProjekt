package de.rechnungflow.cli;

import de.rechnungflow.model.*;
import de.rechnungflow.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class CliApp {
    private final ConsoleIO io = new ConsoleIO();
    private final InvoiceGeneratorService invoiceGeneratorService;
    private final InvoiceService invoiceService;
    private final WorkLogService workLogService;
    private final CleaningObjectService cleaningObjectService;
    private final ClientService clientService;
    private final EmployeeService employeeService;

    public CliApp(
            InvoiceGeneratorService invoiceGeneratorService,
            InvoiceService invoiceService,
            WorkLogService workLogService,
            CleaningObjectService cleaningObjectService,
            ClientService clientService,
            EmployeeService employeeService){
        this.invoiceGeneratorService = invoiceGeneratorService;
        this.invoiceService = invoiceService;
        this.workLogService = workLogService;
        this.cleaningObjectService = cleaningObjectService;
        this.clientService = clientService;
        this.employeeService = employeeService;
    }

    public void run(){
        invoiceService.loadFromFile();
        io.println("=== RechnungFlow CLI ===");

        boolean running = true;
        while(running){
            printMenu();
            int choice = io.readInt("Choose option: ", 0, 8);

            switch (choice){
                case 1 -> createClient();
                case 2 -> createCleaningObject();
                case 3 -> createEmployee();
                case 4 -> addWorkLog();
                case 5 -> generateInvoiceForObject();
                case 6 -> listInvoices();
                case 7 -> markInvoicePaid();
                case 8 -> payPartially();
                case 0 -> {
                    io.println("Bye!");
                    running = false;
                }
            }
            io.println("");
        }
    }

    private void printMenu(){
        io.println("1) Create client");
        io.println("2) Create cleaning Object");
        io.println("3) Create employee");
        io.println("4) Add work log");
        io.println("5) Generate invoice for object (period)");
        io.println("6) List invoices");
        io.println("7) Mark invoice paid");
        io.println("8) Mark invoice partially paid");
        io.println("0) Exit");
    }

    private void generateInvoiceForObject(){
        io.println("--- Generate invoice for object (period) ---");

        printCleaningObjectSummaryHeader();
        for (CleaningObject obj : cleaningObjectService.getAll()){
            printCleaningObjectSummary(obj);
        }

        int objectId = io.readInt("Object ID: ", 1, 100000);
        Map<LocalDate, BigDecimal> hoursByDay = new TreeMap<>();

        for(WorkLog wl : workLogService.getAll()){
            if (wl.getObjectId() == objectId){
                hoursByDay.merge(wl.getDate(), wl.getHours(), BigDecimal::add);
            }
        }

        if (hoursByDay.isEmpty()) {
            io.println("No work logs found for this object");
            return;
        } else {
            io.println("Cleaning days for ObjectID " + objectId + ":");
            for ( var e : hoursByDay.entrySet()){
                io.println(" - " + e.getKey() + " | hours: " + e.getValue());
            }
        }

        LocalDate from = io.readDate("From date (YYYY-MM-DD): ");
        LocalDate to = io.readDate("To date (YYYY-MM-DD)");

        if (to.isBefore(from)){
            io.println("Error: TO date must be after FROM date");
        }

        Invoice invoice = invoiceGeneratorService.generateInvoiceForObject(objectId, from, to);

        if (invoice == null){
            io.println("No invoice generated. Check object ID, client link, or work logs for that period");
            return;
        }

        io.println("Invoice generated");
        io.println("Invoice number: " + invoice.getInvoiceNumber());
        io.println("Total amount: " + invoice.getTotalAmount());
    }

    private void createClient(){
        io.println("--- Create client ---");

        String companyName = io.readLine("Company name: ");
        String contactPerson = io.readLine("Contact person: ");
        String email = io.readLine("Email: ");
        String phone = io.readLine("Phone: ");

        Client client = clientService.createClient(companyName, contactPerson, email, phone);

        io.println("Client created. ID: " + client.getId());

    }

    public void createCleaningObject(){
        io.println("--- Create cleaning Object ---");

        int clientId = io.readInt("Client ID: ", 1, 1000000);
        String name = io.readLine("Object name: ");
        String address = io.readLine("Address of object: ");
        BigDecimal hourlyRate = io.readBigDecimal("Hourly rate: ");

        Client client = clientService.findClientById(clientId);

        if (client == null){
            io.println("Client not found" + clientId);
            return;
        }

        CleaningObject obj = cleaningObjectService.createCleaningObject(clientId, name, address, hourlyRate);
        io.println("Cleaning object created. ID: " + obj.getCleaningObjectId());
    }

    public void createEmployee(){
        io.println("--- Create employee ---");

        String name = io.readLine("Employee name: ");
        String phone = io.readLine("Employee phone: ");
        String email = io.readLine("Employee email: ");

        Employee employee = employeeService.createEmployee(name, phone, email);

        io.println("Employee created.ID: " + employee.getEmployeesId());
    }

    private void addWorkLog(){
        io.println("--- Add work log ---");

        printEmployeeSummaryHeader();
        for (Employee emp : employeeService.getAll()){
                printEmployeesSummary(emp);
        }

        int employeeId = io.readInt("Employee ID: ", 1, 1000000);

        printCleaningObjectSummaryHeader();
        for (CleaningObject obj : cleaningObjectService.getAll()){
            printCleaningObjectSummary(obj);
        }

        int objectId = io.readInt("Object ID: ", 1, 1000000);
        LocalDate date = io.readDate("Date (YYYY-MM-DD)");
        BigDecimal hours = io.readBigDecimal("Hours (e.g. 7.5)");

        WorkLog wl = workLogService.createWorkLog(employeeId, objectId, date, hours);

        io.println("Work log added. ID: " + wl.getId());
    }


    private void createInvoice(){
        io.println("===== Create Invoice =====");

        String companyName = io.readLine("Company name: ");
        String contactPerson = io.readLine("Contact person: ");
        String email = io.readLine("Customer email: ");
        String phone = io.readLine("Phone: ");


        Client client = new Client(companyName, contactPerson, email, phone);


        Invoice invoice = invoiceService.createInvoice(client);

        int itemsCount = io.readInt("How many services are provided?", 1,50);

        for (int i = 1; i <= itemsCount; i++){
            io.println("--- Item " + i + "---");
            String title = io.readLine("Title: ");
            BigDecimal hours = io.readBigDecimal("Hours: ");
            var unitPrice = io.readBigDecimal("Unit price (e.g. 12.50): ");

            InvoiceItem item = new InvoiceItem(title, hours, unitPrice);
            invoice.addItem(item);
            invoiceService.saveToFile();
        }

        io.println("Invoice #" + invoice.getInvoiceNumber() +  "created and saved in memory");

    }

    private void listInvoices(){
        io.println("---Invoices---");
        if (invoiceService.isEmpty()){
            io.println("No invoices yet");
            return;
        }

        printInvoiceSummaryHeader();

        for (Invoice inv : invoiceService.getAll()){
            printInvoiceSummary(inv);
        }

        int number = io.readInt("Show details (invoice number) or  0 to back: ", 0, Integer.MAX_VALUE);
        if (number != 0){
            Invoice inv = invoiceService.findByNumber(number)
                    .orElseThrow(() -> new IllegalArgumentException("Invoice not found: #" + number));
            if(inv == null){
                io.println("Invoice #" + number + " not found.");
            } else {
                printInvoiceDetails(inv);
            }
        }
    }

    private void showInvoice(){
        if (invoiceService.isEmpty()){
            io.println("No invoices");
            return;
        }

        printInvoiceSummaryHeader();
        for (Invoice inv : invoiceService.getAll()){
            printInvoiceSummary(inv);
        }

        int number = io.readInt("Invoice number to show: ", 1, Integer.MAX_VALUE);
        Invoice invoice = invoiceService.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: #" + number));

        if (invoice==null){
            io.println("Invoice #" + number + " not found");
            return;
        }

        printInvoiceDetails(invoice);
    }

    private void printInvoiceSummary(Invoice inv){
        System.out.printf(
                "%-4d | %-15s | %-14s | %12s | %12s | %12s%n",
                inv.getInvoiceNumber(),
                inv.getCustomer().getContactPerson(),
                inv.getStatus(),
                Formatters.money(inv.getTotalAmount()),
                Formatters.money(inv.getPaidAmount()),
                Formatters.money(inv.getOpenAmount())
        );
    }

    private void printEmployeesSummary(Employee emp){
        System.out.printf(
                "%-4d | %-15s | %-14s | %12s%n",
                emp.getEmployeesId(),
                emp.getNameOfEmployee(),
                emp.getPhoneOfEmployee(),
                emp.getEmailOfEmployee()
        );
    }

    private void printEmployeeSummaryHeader(){
        System.out.printf(
                "%-4s | %-15s | %-14s | %12s%n",
                "#",
                "Employee name",
                "Employee phone",
                "Employee email"
        );
    }

    private void printCleaningObjectSummary(CleaningObject obj){
        System.out.printf(
                "%-8s | %-10s | %-20.20s | %-30.30s | %10.2f%n",
                obj.getCleaningObjectId(),
                obj.getClientId(),
                obj.getName(),
                obj.getAddress(),
                obj.getHourlyRate()
        );
    }

    private void printCleaningObjectSummaryHeader(){
        System.out.printf(
                "%-8s | %-10s | %-20s | %-30s | %10s%n",
                "Object #",
                "Client #",
                "Object name",
                "Address",
                "Hourly rate"

        );
    }

    private  void printInvoiceSummaryHeader(){
        System.out.printf(
                "%-4s | %-15s | %-14s | %12s | %12s | %12s%n",
                "#",
                "Client",
                "Status",
                "Total",
                "Paid",
                "Open"
        );
        io.println("-----+-----------------+----------------+--------------+--------------+--------------");
    }

    private void printInvoiceDetails(Invoice inv){
        io.println("===Invoice #" + inv.getInvoiceNumber() + "===");
        io.println("Customer: " + inv.getCustomer().getContactPerson());
        io.println("Status: " + inv.getStatus());
        io.println("Cleaning: ");

        int i = 1;
        for (InvoiceItem item : inv.getItems()){
            io.println(" " + i + ") " + item.getDescription() +
                    " | hours: " + item.getHours()
                + " | price " + item.getPrice());
            i++;
        }
    }

    private void markInvoicePaid(){
        if (invoiceService.isEmpty()){
            io.println("No invoices");
            return;
        }

        for (Invoice invoice : invoiceService.getAll()){
            printInvoiceSummary(invoice);
        }

        int number = io.readInt("Choose the invoice number", 1, Integer.MAX_VALUE);
        try {
            invoiceService.markAsPaid(number);
            io.println("Invoice #" + number + " marked as PAID");
        } catch (RuntimeException e) {
            io.println("Failed: " + e.getMessage());
        }

    }

    private void payPartially(){
        for (Invoice inv : invoiceService.getAll()){
            printInvoiceSummary(inv);
        }

        int invoiceNumber = io.readInt("Which invoice you want to pay partially?", 1, Integer.MAX_VALUE);
        Invoice invoice = invoiceService.findByNumber(invoiceNumber)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found: #" + invoiceNumber));

        printInvoiceDetails(invoice);

        String raw  = io.readLine("How much do you want to pay now?");
        raw = raw.trim().replace(",", ".");

        BigDecimal amount;

        try{
            amount = new BigDecimal(raw);
        } catch (NumberFormatException e){
            io.println("Invalid amount format");
            return;
        }

        boolean ok = invoiceService.payPartially(invoiceNumber, amount);

        if(ok){
            Invoice updated = invoiceService.findByNumber(invoiceNumber)
                    .orElseThrow(() -> new IllegalArgumentException("Invoice not found: #" + invoiceNumber));
            BigDecimal remaining = updated.getTotalAmount().subtract(updated.getPaidAmount());

            if(updated != null) {
                io.println("Paid " + amount + ". Paid total: " + updated.getPaidAmount() + ". Remaining: " + remaining);
            } else {
                io.println("Paid " + amount + ".");
            }
        } else {
            io.println("Payment failed (invoice not found or invalid state/amount).");
        }

    }





}
