public class InvoiceItem {
    private String description;
    private int quantity;
    private double price;

    public InvoiceItem(String description, int quantity, double price){
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public double getTotal(){
        return quantity * price;
    }
}
