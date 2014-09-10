package the.implementer.sse.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class SimpleResource {

    @GET
    public String test() {
        return "test";
    }
}
