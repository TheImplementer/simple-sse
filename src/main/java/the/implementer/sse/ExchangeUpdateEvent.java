package the.implementer.sse;

import java.util.List;

public class ExchangeUpdateEvent {

    private final String exchangeName;
    private final List<Order> offers;
    private final List<Order> demands;

    private ExchangeUpdateEvent(String exchangeName, List<Order> offers, List<Order> demands) {
        this.exchangeName = exchangeName;
        this.offers = offers;
        this.demands = demands;
    }

    public static ExchangeUpdateEvent updateEvent(String exchangeName, List<Order> offers, List<Order> demands) {
        return new ExchangeUpdateEvent(exchangeName, offers, demands);
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public List<Order> getOffers() {
        return offers;
    }

    public List<Order> getDemands() {
        return demands;
    }
}
