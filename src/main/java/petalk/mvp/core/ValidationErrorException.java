package petalk.mvp.core;

import java.util.List;

/**
 * 유효성 검사 오류를 나타내는 클래스입니다.
 */
public class ValidationErrorException extends RuntimeException {
    private final ValidationErrors errors;

    public ValidationErrorException(ValidationErrors errors) {
        this.errors = errors;
    }

    public ValidationErrors getErrors() {
        return errors;
    }
}
