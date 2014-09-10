package the.implementer.sse.exchanges.cryptsy;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class OrderDataResponse extends PublicApiResponse<OrderData> {

    @JsonProperty("return")
    private Map<Currency, OrderData> returnData;

    @Override
    public Map<Currency, OrderData> getData() {
        return returnData;
    }
}
