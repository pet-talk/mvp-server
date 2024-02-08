package petalk.mvp.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResult<T> {
    private T data;
    private String message;

    private ApiResult(String message) {
        this.message = message;
    }

    private ApiResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(data, "OK");
    }

    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(message);
    }

    public static <T> ApiResult<T> validationError(T data) {
        return new ApiResult<>(data, "Validation Error");
    }

}
