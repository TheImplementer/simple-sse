package the.implementer.sse;

import java.util.List;

public class ExchangeUpdateEvent {

    private final String exchangeName;
    private final List<Trade> offers;

    private ExchangeUpdateEvent(String exchangeName, List<Trade> offers) {
        this.exchangeName = exchangeName;
        this.offers = offers;
    }

    public static ExchangeUpdateEvent updateEvent(String exchangeName, List<Trade> offers) {
        return new ExchangeUpdateEvent(exchangeName, offers);
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public List<Trade> getOffers() {
        return offers;
    }
}
