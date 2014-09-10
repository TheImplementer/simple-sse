package the.implementer.sse.exchanges.cryptsy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties({"primaryname", "secondaryname"})
public class OrderData {

    @JsonProperty("marketid")
    private int marketId;

    private String label;

    @JsonProperty("primarycode")
    private Currency primaryCode;

    @JsonProperty("secondarycode")
    private Currency secondaryCode;

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

    public Currency getPrimaryCurrency() {
        return primaryCode;
    }

    public Currency getSecondaryCurrency() {
        return secondaryCode;
    }

    public List<CryptsyOrder> getSellOrders() {
        return sellOrders;
    }

    public List<CryptsyOrder> getBuyOrders() {
        return buyOrders;
    }
}
