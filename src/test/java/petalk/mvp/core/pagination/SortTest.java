package petalk.mvp.core.pagination;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import petalk.mvp.core.annotation.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;

@UnitTest
/**
 * SortTest는 Sort 클래스에 대한 단위 테스트입니다.
 */
@DisplayName("Sort 클래스 단위 테스트")
class SortTest {

    /**
     * @when 정렬 필드와 정렬 방향 그리고 그 사이에 반점이 있다면 정렬을 기본 생성할 때
     * @then Sort을 생성한다.
     */
    @DisplayName("정렬 필드와 정렬 방향 그리고 그 사이에 반점이 있다면 정렬을 기본 생성할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "sortField, ASC",
            "  sortField  , asc  ",
            "  sortField,aSc  "},
            delimiter = ':')
    void createSort(String sortValue) {
        //when
        Sort sort = Sort.fromByDefault(sortValue);

        //then
        assertThat(sort)
                .extracting("sortField", "sortDirection")
                .containsExactly("sortField", Sort.SortDirection.ASC);
    }

    /**
     * @when 정렬 필드를 포함하지 않은 채 정렬을 기본 생성할 때
     * @then 에러를 발생시킨다.
     */
    @Test
    @DisplayName("정렬 필드를 포함하지 않은 채 정렬을 기본 생성할 수 없다.")
    void sortMustHaveFieldInfo() {
        //when
        Throwable throwable = catchThrowable(() -> Sort.fromByDefault("asc"));

        //then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("sort must be in the format of 'field, direction");
    }

    /**
     * @when 정렬 방향을 포함하지 않은 채 정렬을 기본 생성할 때
     * @then 에러를 발생시킨다.
     */
    @Test
    @DisplayName("정렬 방향을 포함하지 않은 채 정렬을 기본 생성할 수 없다.")
    void sortMustHaveDirectionInfo() {
        //when
        Throwable throwable = catchThrowable(() -> Sort.fromByDefault("sortField"));

        //then
        assertThat(throwable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("sort must be in the format of 'field, direction");
    }
}