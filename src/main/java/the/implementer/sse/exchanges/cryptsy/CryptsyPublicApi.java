package the.implementer.sse.exchanges.cryptsy;


import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static java.lang.String.format;

public class CryptsyPublicApi {

    private static final String MARKET_DATA_URL = "%s/api.php?method=marketdatav2";
    private static final String SINGLE_MARKET_DATA_URL = "%s/api.php?method=singlemarketdata&marketid=%s";
    private static final String ORDER_DATA_URL = "%s/api.php?method=orderdatav2";
    private static final String SINGLE_ORDER_DATA_URL = "%s/api.php?method=singleorderdata&marketid=%s";

    private final CryptsyPublicApiUrl cryptsyPublicApiUrl;
    private final ObjectMapper objectMapper;
    private final DataReader dataReader;

    public CryptsyPublicApi(CryptsyPublicApiUrl cryptsyPublicApiUrl, ObjectMapper objectMapper, DataReader dataReader) {
        this.cryptsyPublicApiUrl = cryptsyPublicApiUrl;
        this.objectMapper = objectMapper;
        this.dataReader = dataReader;
    }

    public Map<Currency, MarketData> getMarketData() throws IOException {
        final String marketDataUrl = format(MARKET_DATA_URL, cryptsyPublicApiUrl.get());
        return getData(marketDataUrl, MarketDataResponse.class);
    }

    public Map<Currency, MarketData> getMarketData(long marketId) throws IOException {
        final String marketDataUrl = format(SINGLE_MARKET_DATA_URL, cryptsyPublicApiUrl.get(), marketId);
        return getData(marketDataUrl, MarketDataResponse.class);
    }

    public Map<Currency, OrderData> getOrderData() throws IOException {
        final String marketDataUrl = format(ORDER_DATA_URL, cryptsyPublicApiUrl.get());
        return getData(marketDataUrl, OrderDataResponse.class);
    }

    public Map<Currency, OrderData> getOrderData(long marketId) throws IOException {
        final String marketDataUrl = format(SINGLE_ORDER_DATA_URL, cryptsyPublicApiUrl.get(), marketId);
        return getData(marketDataUrl, OrderDataResponse.class);
    }

    private <R, T extends PublicApiResponse<R>> Map<Currency, R> getData(String url, Class<T> responseClass) throws IOException {
        final T response = objectMapper.readValue(readDataFrom(url), responseClass);
        if (!response.isSuccess()) {
            throw new CryptsyException(format("Remote server reported unsuccessful response for url: %s, error: %s", url, response.getError()));
        }
        return response.getData();
    }

    private String readDataFrom(String url) {
        try {
            return dataReader.read(url);
        } catch (Exception ex) {
            throw new CryptsyConnectionException(format("An error occurred while trying to fetch data from the url: %s", url));
        }
    }
}
