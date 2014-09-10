package the.implementer.sse;

public interface ExchangesService {
    void start();

    void subscribe(ExchangeEventListener eventListener);
}
