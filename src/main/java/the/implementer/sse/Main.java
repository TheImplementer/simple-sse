package the.implementer.sse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.eclipse.jetty.servlet.ServletContextHandler.SESSIONS;
import static org.eclipse.jetty.util.resource.Resource.newClassPathResource;

public class Main {

    public static void main(String[] args) throws Exception {
        final Server server = new Server(9998);

        final ServletContextHandler jerseyContextHandler = createJerseyContextHandler();
        final ServletContextHandler resourcesContextHandler = createResourcesContextHandler();
        final ContextHandlerCollection contextHandlers = new ContextHandlerCollection();
        contextHandlers.setHandlers(new Handler[]{jerseyContextHandler, resourcesContextHandler});

        server.setHandler(contextHandlers);
        server.start();
        server.join();
    }

    private static ServletContextHandler createJerseyContextHandler() {
        final ServletContextHandler jerseyContextHandler = new ServletContextHandler(SESSIONS);
        jerseyContextHandler.setContextPath("/rest");
        final ServletContainer jerseyServletContainer = new ServletContainer(new ApplicationConfig());
        jerseyContextHandler.addServlet(new ServletHolder(jerseyServletContainer), "/*");
        return jerseyContextHandler;
    }

    private static ServletContextHandler createResourcesContextHandler() {
        final ServletContextHandler resourcesContextHandler = new ServletContextHandler(SESSIONS);
        resourcesContextHandler.setContextPath("/");
        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setBaseResource(newClassPathResource("/"));
        resourcesContextHandler.setHandler(resourceHandler);
        return resourcesContextHandler;
    }
}
