package the.implementer.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.hk2.api.Factory;
import the.implementer.sse.exchanges.cryptsy.CryptsyPublicApi;
import the.implementer.sse.exchanges.cryptsy.RemoteDataReader;

import static the.implementer.sse.exchanges.cryptsy.CryptsyPublicApiUrl.defaultCryptsyPublicApiUrl;

public class CryptsyPublicApiFactory implements Factory<CryptsyPublicApi> {

    @Override
    public CryptsyPublicApi provide() {
        return new CryptsyPublicApi(defaultCryptsyPublicApiUrl(), new ObjectMapper(), new RemoteDataReader());
    }

    @Override
    public void dispose(CryptsyPublicApi cryptsyPublicApi) {
    }
}
