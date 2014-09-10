package the.implementer.sse;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import javax.inject.Inject;

public class CustomApplicationEventListener implements ApplicationEventListener {

    private final ExchangesService exchangesService;

    @Inject
    public CustomApplicationEventListener(ExchangesService exchangesService) {
        this.exchangesService = exchangesService;
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        switch (event.getType()) {
            case INITIALIZATION_FINISHED:
                exchangesService.start();
                break;
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }
}
