package the.implementer.sse.exchanges.cryptsy;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class MarketDataResponse extends PublicApiResponse<MarketData> {

    @JsonProperty("return")
    private ReturnData returnData;

    public Map<Currency, MarketData> getMarketData() {
        return returnData.getMarkets();
    }

    @Override
    public Map<Currency, MarketData> getData() {
        return returnData.getMarkets();
    }

    private static class ReturnData {

        private Map<Currency, MarketData> markets;

        public Map<Currency, MarketData> getMarkets() {
            return markets;
        }
    }
}
