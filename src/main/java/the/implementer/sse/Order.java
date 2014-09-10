package the.implementer.sse;

import java.math.BigDecimal;

public class Order {

    private final BigDecimal price;
    private final BigDecimal quantity;

    private Order(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public static Order order(BigDecimal price, BigDecimal quantity) {
        return new Order(price, quantity);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!price.equals(order.price)) return false;
        if (!quantity.equals(order.quantity)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = price.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }
}
