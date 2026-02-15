package de.rechnungflow.cli;

import de.rechnungflow.model.*;
import de.rechnungflow.service.InvoiceService;

import java.math.BigDecimal;

public class CliApp {
    private final ConsoleIO io = new ConsoleIO();
    private final InvoiceGeneratorService invoiceGeneratorService;
    private final InvoiceService invoiceService;
    private final WorkLogService workLogService;
    private final CleaningObjectService cleaningObjectService;
    private final ClientService clientService;

    public CliApp(
            InvoiceGeneratorService invoiceGeneratorService,
            InvoiceService invoiceService,
            WorkLogService workLogService,
            CleaningObjectService cleaningObjectService,
            ClientService clientService
    ){
        this.invoiceGeneratorService = invoiceGeneratorService;
        this.invoiceService = invoiceService;
        this.workLogService = workLogService;
        this.cleaningObjectService = cleaningObjectService;
        this.clientService = clientService;
    }

    public void run(){
        invoiceService.loadFromFile();
        io.println("=== RechnungFlow CLI ===");

        boolean running = true;
        while(running){
            printMenu();
            int choice = io.readInt("Choose option: ", 0, 5);

            switch (choice){
                case 1 -> createInvoice();
                case 2 -> listInvoices();
                case 3 -> showInvoice();
                case 4 -> markInvoicePaid();
                case 5 -> payPartially();
                case 0 -> {
                    io.println("Bye!");
                    running = false;
                }
            }
            io.println("");
        }
    }

    private void printMenu(){
        io.println("1) Create invoice");
        io.println("2) List invoices");
        io.println("3) Show invoice");
        io.println("4) Mark invoice paid");
        io.println("5) Pay partially");
        io.println("0) Exit");
    }

    private void createInvoice(){
        io.println("===== Create Invoice =====");

        String companyName = io.readLine("Company name: ");
        String contactPerson = io.readLine("Contact person: ");
        String email = io.readLine("Customer email: ");
        String phone = io.readLine("Phone: ");


        Client client = new Client(companyName, contactPerson, email, phone);


        Invoice invoice = invoiceService.createInvoice(client);

        int itemsCount = io.readInt("How many items?", 1,50);

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

    private  void printInvoiceSummaryHeader(){
        System.out.printf(
                "%-4s | %-15s | %-14s | %12s | %12s | %12s%n",
                "#",
                "Customer",
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
        io.println("Items: ");

        int i = 1;
        for (InvoiceItem item : inv.getItems()){
            io.println(" " + i + ") " + item.getDescription() +
                    " | qty: " + item.getHours()
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
