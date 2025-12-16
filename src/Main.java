public class Main {
    static void main(String[] args) {
        Customer customer = new Customer("Ivan Petrov", "ivan@gmail.com");

        Invoice invoice = new Invoice("INV-01", customer);

        invoice.addItem(new InvoiceItem("Website development",1,1200));
        invoice.addItem(new InvoiceItem("Hosting 1 year", 1, 120));

        System.out.println("Total invoices: " + invoice.getTotalAmount());
        //System.out.println(invoice.getStatus());
        //invoice.pay(500);
        //System.out.println(invoice.getStatus());
        //invoice.pay(1000);
        //System.out.println(invoice.getStatus());

    }
}
