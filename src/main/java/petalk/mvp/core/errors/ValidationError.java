package petalk.mvp.core.errors;

/**
 * 유효성 검사 오류 정보를 나타내는 클래스입니다.
 */
public class ValidationError {
    private String field;
    private String message;

    public ValidationError(String field, String message) {
        this.message = message;
        this.field = field;
    }

    private ValidationError() {
    }

    public boolean hasName() {
        return message != null;
    }

    public static ValidationError of(String field) {
        return new ValidationError(field, null);
    }

    public static ValidationError of(String field, String message) {
        return new ValidationError(field, message);
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
