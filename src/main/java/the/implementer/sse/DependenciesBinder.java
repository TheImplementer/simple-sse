package the.implementer.sse;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import the.implementer.sse.exchanges.cryptsy.CryptsyPublicApi;

import javax.inject.Singleton;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class DependenciesBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(CryptsyPublicApiFactory.class)
                .to(CryptsyPublicApi.class);
        bind(newScheduledThreadPool(1))
                .to(ScheduledExecutorService.class);
        bind(DefaultExchangesService.class)
                .to(ExchangesService.class)
                .in(Singleton.class);
    }
}
