package the.implementer.sse.exchanges.cryptsy;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RemoteDataReader implements DataReader {

    @Override
    public String read(String from) {
        try {
            final InputStream remoteStream = new URL(from).openStream();
            return IOUtils.toString(remoteStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
