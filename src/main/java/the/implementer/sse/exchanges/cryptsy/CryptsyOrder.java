package the.implementer.sse.exchanges.cryptsy;

import java.math.BigDecimal;

public class CryptsyOrder {

    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal total;

    public CryptsyOrder() {
        // Empty constructor needed by Jackson
    }

    private CryptsyOrder(BigDecimal price, BigDecimal quantity, BigDecimal total) {
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public static CryptsyOrder cryptsyOrder(BigDecimal price, BigDecimal quantity, BigDecimal total) {
        return new CryptsyOrder(price, quantity, total);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CryptsyOrder that = (CryptsyOrder) o;

        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }
}
