package petalk.mvp.core.errors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 유효성 검사 오류들을 관리하는 클래스입니다.
 */
public class ValidationErrors {

    List<ValidationError> errors;

    private ValidationErrors(List<ValidationError> errors) {
        this.errors = new ArrayList<>(errors);
    }

    private ValidationErrors() {
        this.errors = new ArrayList<>();
    }

    public static ValidationErrors from(List<ValidationError> errors) {
        return new ValidationErrors(errors);
    }

    public static ValidationErrors from(ValidationError error) {
        return new ValidationErrors(List.of(error));
    }

    public static ValidationErrors startValidate() {
        return new ValidationErrors();
    }

    public void add(ValidationError error) {
        errors.add(error);
    }

    public void addAll(List<ValidationError> errors) {
        this.errors.addAll(errors);
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    @Override
    public String toString() {
        return errors.stream()
                .map(error -> "Field: '" + error.getField() + "', Message: '" + error.getMessage() + "'")
                .collect(Collectors.joining("; ", "Validation Errors: [", "]"));
    }
}
