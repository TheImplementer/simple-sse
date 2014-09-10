package the.implementer.sse;

public interface ExchangeEventListener {
    void notify(ExchangeUpdateEvent event);
}
