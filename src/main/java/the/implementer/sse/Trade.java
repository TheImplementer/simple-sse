package the.implementer.sse;

import java.math.BigDecimal;

public class Trade {

    private final BigDecimal price;
    private final BigDecimal quantity;

    public Trade(BigDecimal price, BigDecimal quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
