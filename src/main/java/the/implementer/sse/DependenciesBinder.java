package the.implementer.sse;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import the.implementer.sse.exchanges.cryptsy.CryptsyPublicApi;

import javax.inject.Singleton;

public class DependenciesBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(CryptsyPublicApiFactory.class)
                .to(CryptsyPublicApi.class);
        bind(DefaultExchangesService.class)
                .to(ExchangesService.class)
                .in(Singleton.class);
    }
}
