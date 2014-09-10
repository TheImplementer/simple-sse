package the.implementer.sse.exchanges.cryptsy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties({"primaryname", "secondaryname"})
public class MarketData {

    @JsonProperty("marketid")
    private int marketId;

    private String label;

    @JsonProperty("lasttradeprice")
    private BigDecimal lastTradePrice;

    private BigDecimal volume;

    @JsonProperty("lasttradetime")
    private CryptsyDate lastTradeTime;

    @JsonProperty("primarycode")
    private Currency primaryCode;

    @JsonProperty("secondarycode")
    private Currency secondaryCode;

    @JsonProperty("recenttrades")
    private List<Trade> recentTrades;

    @JsonProperty("sellorders")
    private List<CryptsyOrder> sellOrders;

    @JsonProperty("buyorders")
    private List<CryptsyOrder> buyOrders;

    public int getMarketId() {
        return marketId;
    }

    public String getLabel() {
        return label;
    }

    public BigDecimal getLastTradePrice() {
        return lastTradePrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public CryptsyDate getLastTradeTime() {
        return lastTradeTime;
    }

    public Currency getPrimaryCurrency() {
        return primaryCode;
    }

    public Currency getSecondaryCurrency() {
        return secondaryCode;
    }

    public List<Trade> getRecentTrades() {
        return recentTrades;
    }

    public List<CryptsyOrder> getSellOrders() {
        return sellOrders;
    }

    public List<CryptsyOrder> getBuyOrders() {
        return buyOrders;
    }

    public static class Trade {
        private int id;
        private CryptsyDate time;
        private TradeType type;
        private BigDecimal price;
        private BigDecimal quantity;
        private BigDecimal total;

        public int getId() {
            return id;
        }

        public CryptsyDate getTime() {
            return time;
        }

        public TradeType getType() {
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

    public static enum TradeType {
        Buy, Sell;
    }

}
