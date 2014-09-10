package the.implementer.sse.exchanges.cryptsy;

import java.util.Map;

public abstract class PublicApiResponse<T> extends ApiResponse<Map<Currency, T>> {
}
