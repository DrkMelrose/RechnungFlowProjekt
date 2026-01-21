package de.rechnungflow;

import de.rechnungflow.cli.CliApp;

public class Main {
    static void main(String[] args) {
        new CliApp().run();







        //de.rechnungflow.model.Customer customer = new de.rechnungflow.model.Customer("Ivan Petrov", "ivan@gmail.com");

        //de.rechnungflow.model.Invoice invoice = new de.rechnungflow.model.Invoice("INV-01", customer);

        //invoice.addItem(new de.rechnungflow.model.InvoiceItem("Website development",1,BigDecimal.valueOf(1200)));
        //invoice.addItem(new de.rechnungflow.model.InvoiceItem("Hosting 1 year", 1, BigDecimal.valueOf(120)));

        //System.out.println("Status: " + invoice.getStatus());
        //invoice.send();
        //System.out.println("Status: " + invoice.getStatus());

        //invoice.pay(BigDecimal.valueOf(500));
        //System.out.println("Paid: " + invoice.getPaidAmount());
        //System.out.println("Open:" + invoice.getOpenAmount());
        //System.out.println("Status:" + invoice.getStatus());

        //invoice.setDueDate(java.time.LocalDate.now().minusDays(1));
        //invoice.updateOverdueStatus();
        //System.out.println("Status after overdue check: " + invoice.getStatus());

        //invoice.pay(BigDecimal.valueOf(1000));
        //System.out.println("Status: " + invoice.getStatus());


        //System.out.println("Total invoices: " + invoice.getTotalAmount());


        //System.out.println(invoice.getStatus());
        //invoice.pay(500);
        //System.out.println(invoice.getStatus());
        //invoice.pay(1000);
        //System.out.println(invoice.getStatus());

    }
}
