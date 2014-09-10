package the.implementer.sse.exchanges.cryptsy;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static the.implementer.sse.exchanges.cryptsy.CryptsyOrder.cryptsyOrder;
import static the.implementer.sse.exchanges.cryptsy.CryptsyPublicApiUrl.defaultCryptsyPublicApiUrl;
import static the.implementer.sse.exchanges.cryptsy.Currency.BTC;
import static the.implementer.sse.exchanges.cryptsy.Currency.LTC;

public class CryptsyPublicApiTest {

    @Test(expected = CryptsyConnectionException.class)
    public void shouldReportAConnectionErrorIfItCouldNotReadTheData() throws Exception {
        final DataReader dataReader = mock(DataReader.class);
        when(dataReader.read(anyString())).thenThrow(new RuntimeException());
        final CryptsyPublicApi cryptsyPublicApi = new CryptsyPublicApi(defaultCryptsyPublicApiUrl(), new ObjectMapper(), dataReader);

        cryptsyPublicApi.getMarketData();
    }

    @Test(expected = CryptsyException.class)
    public void shouldReportAnExceptionIfTheServerAnsweredWithAnUnsuccessfulResponse() throws Exception {
        final DataReader dataReader = mock(DataReader.class);
        when(dataReader.read(anyString())).thenReturn(anUnsuccessfulResponse());
        final CryptsyPublicApi cryptsyPublicApi = new CryptsyPublicApi(defaultCryptsyPublicApiUrl(), new ObjectMapper(), dataReader);

        cryptsyPublicApi.getMarketData();
    }

    @Test
    public void shouldParseDataCorrectlyForValidMarketData() throws Exception {
        final DataReader dataReader = mock(DataReader.class);
        when(dataReader.read(anyString())).thenReturn(aValidMaketDataJson());
        final CryptsyPublicApi cryptsyPublicApi = new CryptsyPublicApi(defaultCryptsyPublicApiUrl(), new ObjectMapper(), dataReader);

        final Map<Currency, MarketData> marketData = cryptsyPublicApi.getMarketData();

        assertThat(marketData.keySet().size(), is(1));
        assertThat(marketData.keySet(), hasItem(LTC));
        final MarketData data = marketData.get(LTC);
        assertThat(data.getMarketId(), is(3));
        assertThat(data.getLabel(), is("LTC/BTC"));
        assertThat(data.getLastTradePrice(), is(new BigDecimal("0.01255044")));
        assertThat(data.getVolume(), is(new BigDecimal("5115.86405395")));
        assertThat(data.getLastTradeTime(), is(new CryptsyDate("2014-07-25 19:20:10")));
        assertThat(data.getPrimaryCurrency(), is(LTC));
        assertThat(data.getSecondaryCurrency(), is(BTC));
        assertThat(data.getSellOrders(), hasSize(100));
        assertThat(data.getSellOrders(), is(ordersFromExample("cryptsy-market-data-example-sells.csv")));
        assertThat(data.getBuyOrders(), hasSize(100));
        assertThat(data.getBuyOrders(), is(ordersFromExample("cryptsy-market-data-example-buys.csv")));
    }

    @Test
    public void shouldParseDataCorrectlyForValidOrderData() throws Exception {
        final DataReader dataReader = mock(DataReader.class);
        when(dataReader.read(anyString())).thenReturn(aValidOrderDataJson());
        final CryptsyPublicApi cryptsyPublicApi = new CryptsyPublicApi(defaultCryptsyPublicApiUrl(), new ObjectMapper(), dataReader);

        final Map<Currency, OrderData> orderData = cryptsyPublicApi.getOrderData();

        assertThat(orderData.size(), is(1));
        assertThat(orderData.keySet(), hasItem(LTC));
        final OrderData data = orderData.get(LTC);
        assertThat(data.getMarketId(), is(3));
        assertThat(data.getLabel(), is("LTC/BTC"));
        assertThat(data.getPrimaryCurrency(), is(LTC));
        assertThat(data.getSecondaryCurrency(), is(BTC));
        assertThat(data.getSellOrders(), hasSize(100));
        assertThat(data.getSellOrders(), is(ordersFromExample("cryptsy-order-data-example-sells.csv")));
        assertThat(data.getBuyOrders(), hasSize(100));
        assertThat(data.getBuyOrders(), is(ordersFromExample("cryptsy-order-data-example-buys.csv")));
    }


    private List<CryptsyOrder> ordersFromExample(String filename) throws URISyntaxException, IOException {
        final URL sampleFileUrl = CryptsyPublicApi.class.getClassLoader().getResource(filename);
        return readAllLines(get(sampleFileUrl.toURI())).parallelStream().map(line -> {
            final String[] values = line.split(",");
            return cryptsyOrder(new BigDecimal(values[0]), new BigDecimal(values[1]), new BigDecimal(values[2]));
        }).collect(toList());
    }

    private String aValidMaketDataJson() throws URISyntaxException, IOException {
        return getFileContent("cryptsy-market-data-example.json");
    }

    private String aValidOrderDataJson() throws IOException, URISyntaxException {
        return getFileContent("cryptsy-order-data-example.json");
    }

    private String getFileContent(String filename) throws IOException, URISyntaxException {
        final URL sampleFileUrl = CryptsyPublicApiTest.class.getClassLoader().getResource(filename);
        return new String(readAllBytes(get(sampleFileUrl.toURI())), Charset.forName("UTF-8"));
    }

    private String anUnsuccessfulResponse() {
        return "{ \"success\": 0 }";
    }
}