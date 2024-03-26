package petalk.mvp.application.vet.query.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import petalk.mvp.application.vet.query.GetVetsQueryParameter;
import petalk.mvp.application.vet.query.in.GetVetsQuery;
import petalk.mvp.core.annotation.UnitTest;
import petalk.mvp.core.errors.ValidationErrorException;
import petalk.mvp.core.pagination.Sort;
import petalk.mvp.domain.vet.AnimalType;
import petalk.mvp.domain.vet.SymptomType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.BDDAssertions.catchThrowable;

/**
 * 수의사 리스트 조회 요청 검증에 대한 서비스 단위 테스트
 */
@UnitTest
@DisplayName("수의사 리스트 조회 요청 검증에 대한 서비스 단위 테스트")
class GetVetsRequestValidatorTest {

    /**
     * @when 수의사 조회 요청을 할 때
     * @then 요청 파라미터를 생성한다.
     */
    @Test
    void request() {
        //when
        GetVetsQueryParameter getVetsQueryParameter = createParameter();

        GetVetsRequestValidator validator = new GetVetsRequestValidator();

        GetVetsQuery.GetVetsQueryRequest getVetsQueryRequest = GetVetsQuery.GetVetsQueryRequest.of(getVetsQueryParameter, validator);

        //then
        assertThat(getVetsQueryRequest)
                .extracting("parameter")
                .isEqualTo(getVetsQueryParameter);
    }

    private static GetVetsQueryParameter createParameter() {
        int size = 10;
        return createParameter(size);
    }

    private static GetVetsQueryParameter createParameter(int size) {
        return createParameter(size, "sortField, ASC");
    }

    private static GetVetsQueryParameter createParameter(String sortValue) {
        return createParameter(10, sortValue);
    }

    private static GetVetsQueryParameter createParameter(int size, String sortValue) {
        String cursor = "cursor";
        Sort sort = Sort.fromByDefault(sortValue);
        SymptomType symptomType = SymptomType.BLOOD;
        AnimalType animalType = AnimalType.CAT;
        String name = "name";
        GetVetsQueryParameter getVetsQueryParameter = new GetVetsQueryParameter(size, cursor, sort, symptomType, animalType, name);
        return getVetsQueryParameter;
    }

    /**
     * @when 수의사 조회 제한 갯수를 0 이하로 요청할 때
     * @then 에러를 발생한다.
     */
    @ParameterizedTest
    @CsvSource(value = {"0", "-1", "-10"})
    @DisplayName("수의사 조회 제한 갯수를 0 이하로 요청하지 못한다.")
    void cantRequestWithSizeLte0(int size) {
        //when
        GetVetsQueryParameter parameter = createParameter(size);
        GetVetsRequestValidator validator = new GetVetsRequestValidator();

        Throwable throwable = catchThrowable(() -> GetVetsQuery.GetVetsQueryRequest.of(parameter, validator));

        //then
        assertThat(throwable)
                .isInstanceOf(ValidationErrorException.class)
                .extracting("errors")
                .extracting("errors").asList()
                .extracting("field", "message")
                .containsExactly(tuple("parameter.size", "size는 0이하일 수 없습니다."));
    }

    /**
     * @when 수의사 조회 정렬 기준을 빈 채로 요청하면
     * @then 에러를 발생한다.
     */
    @ParameterizedTest
    @CsvSource(value = {
            "  , asc  "},
            delimiter = ':')
    @DisplayName("수의사 조회 요청 시 정렬 방식을 포함해야만 한다.")
    void cantRequestWithOutSortValue(String sortValue) {
        //when
        GetVetsRequestValidator validator = new GetVetsRequestValidator();

        Throwable throwable = catchThrowable(() -> GetVetsQuery.GetVetsQueryRequest.of(createParameter(sortValue), validator));

        //then

        assertThat(throwable)
                .isInstanceOf(ValidationErrorException.class)
                .extracting("errors")
                .extracting("errors").asList()
                .extracting("field", "message")
                .containsExactly(tuple("parameter.sort.field", "정렬 필드가 비어있습니다."));

    }
}