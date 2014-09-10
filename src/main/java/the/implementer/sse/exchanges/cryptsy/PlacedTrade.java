package the.implementer.sse.exchanges.cryptsy;

import java.math.BigDecimal;

public class PlacedTrade {
    private int id;
    private CryptsyDate time;
    private MarketData.TradeType type;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal total;

    public int getId() {
        return id;
    }

    public CryptsyDate getTime() {
        return time;
    }

    public MarketData.TradeType getType() {
        return type;
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
}
