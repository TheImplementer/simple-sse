package the.implementer.sse.exchanges.cryptsy;

public abstract class ApiResponse<T> {

    private int success;
    private String error;

    public boolean isSuccess() {
        return success != 0;
    }

    public String getError() {
        return error;
    }

    public abstract T getData();
}
