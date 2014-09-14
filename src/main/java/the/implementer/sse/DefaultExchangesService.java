package the.implementer.sse;

import the.implementer.sse.exchanges.cryptsy.CryptsyOrder;
import the.implementer.sse.exchanges.cryptsy.CryptsyPublicApi;
import the.implementer.sse.exchanges.cryptsy.Currency;
import the.implementer.sse.exchanges.cryptsy.MarketData;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;
import static the.implementer.sse.ExchangeUpdateEvent.updateEvent;
import static the.implementer.sse.Order.order;
import static the.implementer.sse.exchanges.cryptsy.Currency.LTC;

public class DefaultExchangesService implements ExchangesService {

    private static final int UPDATE_INTERVAL = 2;

    private final List<ExchangeEventListener> eventListeners = new ArrayList<>();
    private final CryptsyPublicApi cryptsyPublicApi;
    private final ScheduledExecutorService scheduledExecutorService;

    private ScheduledFuture<?> scheduledFuture;

    @Inject
    public DefaultExchangesService(CryptsyPublicApi cryptsyPublicApi, ScheduledExecutorService scheduledExecutorService) {
        this.cryptsyPublicApi = cryptsyPublicApi;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void start() {
        scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new ExchangeUpdater(), 0, UPDATE_INTERVAL, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        if (scheduledFuture != null) scheduledFuture.cancel(true);
    }

    @Override
    public void subscribe(ExchangeEventListener eventListener) {
        synchronized (eventListeners) {
            eventListeners.add(eventListener);
        }
    }

    @Override
    public void unsubscribe(ExchangeEventListener eventListener) {
        synchronized (eventListeners) {
            eventListeners.remove(eventListener);
        }
    }

    private class ExchangeUpdater implements Runnable {

        @Override
        public void run() {
            synchronized (eventListeners) {
                if (!eventListeners.isEmpty()) {
                    final Map<Currency, MarketData> marketData = cryptsyPublicApi.getMarketData(3);
                    final MarketData ltcMarketData = marketData.get(LTC);
                    final List<Order> offers = tradesFor(ltcMarketData.getBuyOrders());
                    final List<Order> demands = tradesFor(ltcMarketData.getSellOrders());
                    final ExchangeUpdateEvent updateEvent = updateEvent("Cryptsy", offers, demands);
                    eventListeners.forEach(listener -> listener.notify(updateEvent));
                }
            }
        }

        private List<Order> tradesFor(List<CryptsyOrder> cryptsyOrders) {
            return cryptsyOrders.stream().map(order -> order(order.getPrice(), order.getQuantity())).collect(toList());
        }
    }
}
