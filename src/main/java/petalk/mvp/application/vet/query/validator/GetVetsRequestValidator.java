package petalk.mvp.application.vet.query.validator;

import org.springframework.util.StringUtils;

import petalk.mvp.application.vet.query.GetVetsQueryParameter;
import petalk.mvp.application.vet.query.in.GetVetsQuery;

import petalk.mvp.core.errors.ValidationError;
import petalk.mvp.core.errors.ValidationErrorException;
import petalk.mvp.core.errors.ValidationErrors;
import petalk.mvp.core.pagination.Sort;

/**
 * GetVetsValidator는 수의사 목록 조회 유스케이스의 유효성 검사를 담당합니다.
 */
public class GetVetsRequestValidator {

    public void validate(GetVetsQuery.GetVetsQueryRequest request) {
        ValidationErrors errors = ValidationErrors.startValidate();

        GetVetsQueryParameter parameter = request.getParameter();

        validate(parameter, errors);

        if (errors.hasError()) {
            throw new ValidationErrorException(errors);
        }

    }

    private void validate(GetVetsQueryParameter parameter, ValidationErrors errors) {
        if (parameter.getSize() <= 0) {
            errors.add(ValidationError.of("parameter.size", "size는 0이하일 수 없습니다."));
        }

        validate(parameter.getSort(), errors);

    }

    private void validate(Sort sort, ValidationErrors errors) {
        if (!StringUtils.hasText(sort.getSortField())) {
            errors.add(ValidationError.of("parameter.sort.field", "정렬 필드가 비어있습니다."));
        }
    }

}
