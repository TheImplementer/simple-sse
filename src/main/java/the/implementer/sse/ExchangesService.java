package the.implementer.sse;

public interface ExchangesService {
    void start();

    void stop();

    void subscribe(ExchangeEventListener eventListener);
}
