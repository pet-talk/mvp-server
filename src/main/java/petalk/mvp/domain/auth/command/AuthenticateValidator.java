package petalk.mvp.domain.auth.command;

import org.springframework.util.Assert;
import petalk.mvp.domain.auth.command.in.AuthenticateUsecase;
import petalk.mvp.core.ValidationError;
import petalk.mvp.core.ValidationErrorException;
import petalk.mvp.core.ValidationErrors;
import petalk.mvp.core.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 인증 요청을 검증하는 Validator입니다.
 */
@Validator
public class AuthenticateValidator {

    public void validate(AuthenticateUsecase.AuthenticateCommand command) {
        Assert.notNull(command, "command는 null일 수 없습니다.");

        ValidationErrors errors = ValidationErrors.from();

        List<ValidationError> validationErrors = validateCommand(command);
        errors.addAll(validationErrors);

        if (!errors.hasError()) {
            throw new ValidationErrorException(errors);
        }

    }

    private List<ValidationError> validateCommand(AuthenticateUsecase.AuthenticateCommand command) {
        List<ValidationError> errors = new ArrayList<>();

        if (command.getCode() == null) {
            errors.add(ValidationError.of("token", "토큰이 존재하지 않습니다."));
        }

        if (command.getCode() != null) {
            List<ValidationError> validationErrors = validateToken(command);
            errors.addAll(validationErrors);
        }
        if (command.getSocialType() == null) {
            errors.add(ValidationError.of("socialType", "소셜 타입이 존재하지 않습니다."));
        }
        return errors;
    }

    private List<ValidationError> validateToken(AuthenticateUsecase.AuthenticateCommand command) {
        List<ValidationError> errors = new ArrayList<>();
        AuthorizationCode token = command.getCode();

        if (token.getValue() == null) {
            errors.add(ValidationError.of("token.value", "토큰 값이 존재하지 않습니다."));
            return errors;
        }

        String value = token.getValue();
        if (value.isBlank()) {
            errors.add(ValidationError.of("token.value", "토큰 값이 비어있습니다."));
        }

        return errors;
    }
}
