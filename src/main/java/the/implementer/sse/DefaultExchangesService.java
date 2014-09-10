package the.implementer.sse;

import the.implementer.sse.exchanges.cryptsy.CryptsyPublicApi;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DefaultExchangesService implements ExchangesService {

    private final List<ExchangeEventListener> eventsListeners = new ArrayList<>();
    private final CryptsyPublicApi cryptsyPublicApi;

    @Inject
    public DefaultExchangesService(CryptsyPublicApi cryptsyPublicApi) {
        this.cryptsyPublicApi = cryptsyPublicApi;
    }

    @Override
    public void start() {
    }

    @Override
    public void subscribe(ExchangeEventListener eventListener) {
        eventsListeners.add(eventListener);
    }
}
