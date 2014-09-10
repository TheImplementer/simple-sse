package the.implementer.sse.exchanges.cryptsy;

import the.implementer.sse.Value;

public class CryptsyPublicApiUrl implements Value<String> {

    private static final String DEFAULT_API_URL = "http://pubapi.cryptsy.com";

    private final String url;

    private CryptsyPublicApiUrl(String url) {
        this.url = url;
    }

    public static CryptsyPublicApiUrl cryptsyPublicApiUrl(String url) {
        return new CryptsyPublicApiUrl(url);
    }

    public static CryptsyPublicApiUrl defaultCryptsyPublicApiUrl() {
        return new CryptsyPublicApiUrl(DEFAULT_API_URL);
    }

    @Override
    public String get() {
        return url;
    }
}
