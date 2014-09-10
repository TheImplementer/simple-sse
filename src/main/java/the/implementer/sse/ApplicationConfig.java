package the.implementer.sse;

import org.glassfish.jersey.server.ResourceConfig;
import the.implementer.sse.rest.SimpleResource;

public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(SimpleResource.class);
    }
}
