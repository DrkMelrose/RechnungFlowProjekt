public class Main {
    static void main(String[] args) {
        Customer customer = new Customer("Ivan Petrov", "ivan@gmail.com");

        Invoice invoice = new Invoice("INV-01", customer);

        invoice.addItem(new InvoiceItem("Website development",1,1200));
        invoice.addItem(new InvoiceItem("Hosting 1 year", 1, 120));

        System.out.println("Status: " + invoice.getStatus());
        invoice.send();
        System.out.println("Status: " + invoice.getStatus());

        invoice.pay(500);
        System.out.println("Paid: " + invoice.getPaidAmount());
        System.out.println("Open:" + invoice.getOpenAmount());
        System.out.println("Status:" + invoice.getStatus());

        invoice.setDueDate(java.time.LocalDate.now().minusDays(1));
        invoice.updateOverdueStatus();
        System.out.println("Status after overdue check: " + invoice.getStatus());

        invoice.pay(1000);
        System.out.println("Status: " + invoice.getStatus());


        System.out.println("Total invoices: " + invoice.getTotalAmount());
        //System.out.println(invoice.getStatus());
        //invoice.pay(500);
        //System.out.println(invoice.getStatus());
        //invoice.pay(1000);
        //System.out.println(invoice.getStatus());

    }
}
