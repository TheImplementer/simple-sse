package the.implementer.sse.rest;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import the.implementer.sse.ExchangeEventListener;
import the.implementer.sse.ExchangeUpdateEvent;
import the.implementer.sse.ExchangesService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

@Path("/test")
public class SimpleResource {

    private final ExchangesService exchangesService;

    @Inject
    public SimpleResource(ExchangesService exchangesService) {
        this.exchangesService = exchangesService;
    }

    @GET
    public String test() {
        return "test";
    }

    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    @Path("sse")
    public EventOutput sse() {
        final EventOutput eventOutput = new EventOutput();
        final NotificationThread notificationThread = new NotificationThread(exchangesService, eventOutput);
        exchangesService.subscribe(notificationThread);
        notificationThread.start();
        return eventOutput;
    }

    public static class NotificationThread extends Thread implements ExchangeEventListener {

        private final BlockingQueue<ExchangeUpdateEvent> eventQueue = new SynchronousQueue<>();

        private final ExchangesService exchangesService;
        private final EventOutput eventOutput;

        public NotificationThread(ExchangesService exchangesService, EventOutput eventOutput) {
            this.exchangesService = exchangesService;
            this.eventOutput = eventOutput;
            exchangesService.subscribe(this);
        }

        @Override
        public void run() {
            while (true) {
                final ExchangeUpdateEvent event;
                try {
                    event = eventQueue.take();
                    sendUpdateEvent(event);
                } catch (InterruptedException e) {
                    throw new RuntimeException("An exception occurred while waiting for events.", e);
                } catch (IOException e) {
                    exchangesService.unsubscribe(this);
                    break;
                }
            }
        }

        private void sendUpdateEvent(ExchangeUpdateEvent event) throws IOException {
            final OutboundEvent.Builder outputEventBuilder = new OutboundEvent.Builder();
            outputEventBuilder.mediaType(MediaType.APPLICATION_JSON_TYPE);
            outputEventBuilder.data(ExchangeUpdateEvent.class, event);
            eventOutput.write(outputEventBuilder.build());
        }

        @Override
        public void notify(ExchangeUpdateEvent event) {
            try {
                eventQueue.put(event);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
