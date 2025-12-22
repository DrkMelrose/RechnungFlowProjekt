import java.math.BigDecimal;

public class InvoiceItem {
    private String description;
    private int quantity;
    private BigDecimal price;

    public InvoiceItem(String description, int quantity, BigDecimal price){
        if (description == null || description.isBlank()){
            throw new IllegalArgumentException("Description must not be blank");
        }
        if (quantity <= 0){
            throw new IllegalArgumentException("The number must be positiv");
        }

        if (price == null){
            throw new IllegalArgumentException("The price must not be null");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("The price must be positive");
        }

        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }

    public BigDecimal getTotal(){
        return price.multiply(BigDecimal.valueOf(quantity)) ;
    }
}
