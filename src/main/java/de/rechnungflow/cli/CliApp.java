package de.rechnungflow.cli;

import de.rechnungflow.model.Customer;
import de.rechnungflow.model.Invoice;
import de.rechnungflow.model.InvoiceItem;
import de.rechnungflow.service.InvoiceService;

public class CliApp {
    private final ConsoleIO io = new ConsoleIO();
    private final InvoiceService service = new InvoiceService();

    public void run(){
        service.loadFromFile();
        io.println("=== RechnungFlow CLI ===");

        boolean running = true;
        while(running){
            printMenu();
            int choice = io.readInt("Choose option: ", 0, 3);

            switch (choice){
                case 1 -> createInvoice();
                case 2 -> listInvoices();
                case 3 -> showInvoice();
                case 4 -> markInvoicePaid();
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
        io.println("0) Exit");
    }

    private void createInvoice(){
        io.println("===== Create Invoice =====");

        String customerName = io.readLine("Customer name: ");
        String customerEmail = io.readLine("Customer email: ");

        Customer customer = new Customer(customerName, customerEmail);


        Invoice invoice = service.createInvoice(customer);

        int itemsCount = io.readInt("How many items?", 1,50);

        for (int i = 1; i <= itemsCount; i++){
            io.println("--- Item " + i + "---");
            String title = io.readLine("Title: ");
            int qty = io.readInt("Quantity: ", 1, 1000);
            var unitPrice = io.readBigDecimal("Unit price (e.g. 12.50): ");

            InvoiceItem item = new InvoiceItem(title, qty, unitPrice);
            invoice.addItem(item);
            service.saveToFile();
        }

        io.println("Invoice #" + invoice.getInvoiceNumber() +  "created and saved in memory");

    }

    private void listInvoices(){
        io.println("---Invoices---");
        if (service.isEmpty()){
            io.println("No invoices yet");
            return;
        }

        for (Invoice inv : service.getAll()){
            printInvoiceSummary(inv);
        }

        int number = io.readInt("Show details (invoice number) or  0 to back: ", 0, Integer.MAX_VALUE);
        if (number != 0){
            Invoice inv = service.findByNumber(number);
            if(inv == null){
                io.println("Invoice #" + number + " not found.");
            } else {
                printInvoiceDetails(inv);
            }
        }
    }

    private void showInvoice(){
        if (service.isEmpty()){
            io.println("No invoices");
            return;
        }

        for (Invoice inv : service.getAll()){
            printInvoiceSummary(inv);
        }

        int number = io.readInt("Invoice number to show: ", 1, Integer.MAX_VALUE);
        Invoice invoice = service.findByNumber(number);

        if (invoice==null){
            io.println("Invoice #" + number + " not found");
            return;
        }

        printInvoiceDetails(invoice);
    }

    private void printInvoiceSummary(Invoice inv){
        io.println("#" + inv.getInvoiceNumber() + "|" + inv.getCustomer().getName() + "|" + inv.getStatus());
    }

    private void printInvoiceDetails(Invoice inv){
        io.println("===Invoice #" + inv.getInvoiceNumber() + "===");
        io.println("Customer: " + inv.getCustomer().getName());
        io.println("Status: " + inv.getStatus());
        io.println("Items: ");

        int i = 1;
        for (InvoiceItem item : inv.getItems()){
            //io.println(" " + i + ") " + item.toString());
            //i++;
            io.println(" " + i + ") " + item.getDescription() +
                    " | qty: " + item.getQuantity()
                + " | price " + item.getPrice());
            i++;
        }
    }

    private void markInvoicePaid(){
        if (service.isEmpty()){
            io.println("No invoices");
            return;
        }

        int number = io.readInt("Invoice number to mark as PAID", 1, Integer.MAX_VALUE);
        boolean ok = service.markAsPaid(number);

        if (!ok) io.println("Invoice #" + number + "not found");
        else io.println("Invoice #" + number + "marked as PAID");
    }





}
