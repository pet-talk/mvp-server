package petalk.mvp.application.auth.command.validator;

import org.springframework.util.StringUtils;
import petalk.mvp.application.auth.command.in.AuthenticateUsecase;
import petalk.mvp.core.Validator;
import petalk.mvp.core.errors.ValidationError;
import petalk.mvp.core.errors.ValidationErrorException;
import petalk.mvp.core.errors.ValidationErrors;

/**
 * 인증 요청을 검증하는 Validator입니다.
 */
@Validator
public class AuthenticateValidator {

    public void validate(AuthenticateUsecase.AuthenticateCommand command) {

        ValidationErrors errors = ValidationErrors.startValidate();

        if (!StringUtils.hasText(command.getToken().getValue())) {
            errors.add(ValidationError.of("tokenValue", "토큰 값이 비어있습니다."));
        }

        if (!StringUtils.hasText(command.getToken().getType())) {
            errors.add(ValidationError.of("tokenType", "토큰 타입이 비어있습니다."));
        }

        if (errors.hasError()) {
            throw new ValidationErrorException(errors);
        }
    }
}
