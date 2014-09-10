package the.implementer.sse;

import org.glassfish.jersey.server.ResourceConfig;
import the.implementer.sse.rest.SimpleResource;

public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        register(new DependenciesBinder());
        register(SimpleResource.class);
        register(CustomApplicationEventListener.class);
    }
}
