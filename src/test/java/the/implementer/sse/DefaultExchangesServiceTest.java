package the.implementer.sse;

import org.junit.Test;
import the.implementer.sse.exchanges.cryptsy.CryptsyPublicApi;
import the.implementer.sse.exchanges.cryptsy.Currency;
import the.implementer.sse.exchanges.cryptsy.MarketData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static the.implementer.sse.Order.order;
import static the.implementer.sse.exchanges.cryptsy.CryptsyOrder.cryptsyOrder;
import static the.implementer.sse.exchanges.cryptsy.Currency.LTC;

public class DefaultExchangesServiceTest {

    @Test
    public void shouldNotifyTheRegisteredListenersWithTheMarketData() throws Exception {
        final CryptsyPublicApi cryptsyApi = mock(CryptsyPublicApi.class);
        when(cryptsyApi.getMarketData()).thenReturn(testMarketData());
        final ManualScheduledExecutorService executorService = new ManualScheduledExecutorService(1);
        final ExchangesService exchangesService = new DefaultExchangesService(cryptsyApi, executorService);
        final SpyExchangeEventListener exchangeEventListener = new SpyExchangeEventListener();
        exchangesService.subscribe(exchangeEventListener);

        exchangesService.start();
        executorService.runCommand();

        assertThat(exchangeEventListener.event.getOffers(), contains(order(new BigDecimal("1.0"), new BigDecimal("2.0"))));
        assertThat(exchangeEventListener.event.getDemands(), contains(order(new BigDecimal("3.0"), new BigDecimal("4.0"))));
    }

    private Map<Currency, MarketData> testMarketData() {
        final Map<Currency, MarketData> marketData = new HashMap<>();
        final MarketData testMarketData = MarketData
                .builder()
                .withBuyOrders(asList(cryptsyOrder(new BigDecimal("1.0"), new BigDecimal("2.0"), null)))
                .withSellOrders(asList(cryptsyOrder(new BigDecimal("3.0"), new BigDecimal("4.0"), null)))
                .build();
        marketData.put(LTC, testMarketData);
        return marketData;
    }

    private static class SpyExchangeEventListener implements ExchangeEventListener {

        public ExchangeUpdateEvent event;

        @Override
        public void notify(ExchangeUpdateEvent event) {
            this.event = event;
        }
    }

    private static class ManualScheduledExecutorService extends ScheduledThreadPoolExecutor {

        private Runnable command;

        public ManualScheduledExecutorService(int corePoolSize) {
            super(corePoolSize);
        }

        @Override
        public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            this.command = command;
            return null;
        }

        public void runCommand() {
            command.run();
        }
    }
}