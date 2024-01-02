package petalk.mvp.core;

public class ApiResponse<T> {
    private T data;
    private String message;

    private ApiResponse(String message) {
        this.message = message;
    }

    private ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, "OK");
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(message);
    }

    public static <T> ApiResponse<T> validationError(T data) {
        return new ApiResponse<>(data, "Validation Error");
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
