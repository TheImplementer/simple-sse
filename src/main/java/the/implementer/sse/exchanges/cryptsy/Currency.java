package the.implementer.sse.exchanges.cryptsy;

public enum Currency {

    BTC("BTC", "BitCoin"),
    LTC("LTC", "LiteCoin");

    private final String code;
    private final String displayName;

    private Currency(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
