package petalk.mvp.application.auth.command.validator;

import org.springframework.util.Assert;
import petalk.mvp.core.errors.ValidationError;
import petalk.mvp.core.errors.ValidationErrorException;
import petalk.mvp.core.errors.ValidationErrors;
import petalk.mvp.core.Validator;

/**
 * 인증 요청을 검증하는 Validator입니다.
 */
@Validator
public class AuthenticateValidator {
    public void validate(String tokenValue, String socialTypeName) {
        Assert.notNull(tokenValue, "tokenValue must not be null");
        Assert.notNull(socialTypeName, "socialTypeName must not be null");

        ValidationErrors errors = ValidationErrors.from();

        if (tokenValue.isBlank()) {
            errors.add(ValidationError.of("tokenValue", "토큰 값이 비어있습니다."));
        }

        if (socialTypeName.isBlank()) {
            errors.add(ValidationError.of("socialTypeName", "소셜 타입이 비어있습니다."));
        }

        if (errors.hasError()) {
            throw new ValidationErrorException(errors);
        }
    }
}
