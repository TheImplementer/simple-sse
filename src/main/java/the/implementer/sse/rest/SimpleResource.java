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
        final NotificationThread notificationThread = new NotificationThread(eventOutput);
        exchangesService.subscribe(notificationThread);
        notificationThread.start();
        return eventOutput;
    }

    public static class NotificationThread extends Thread implements ExchangeEventListener {

        private final BlockingQueue<ExchangeUpdateEvent> eventQueue = new SynchronousQueue<>();

        private final EventOutput eventOutput;

        public NotificationThread(EventOutput eventOutput) {
            this.eventOutput = eventOutput;
        }

        @Override
        public void run() {
            while (true) {
                final ExchangeUpdateEvent event;
                try {
                    event = eventQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                sendUpdateEvent(event);
            }
        }

        private void sendUpdateEvent(ExchangeUpdateEvent event) {
            final OutboundEvent.Builder outputEventBuilder = new OutboundEvent.Builder();
            outputEventBuilder.name("update-event");
            outputEventBuilder.data(ExchangeUpdateEvent.class, event);
            try {
                eventOutput.write(outputEventBuilder.build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    eventOutput.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
