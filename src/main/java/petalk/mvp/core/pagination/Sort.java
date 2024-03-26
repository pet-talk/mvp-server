package petalk.mvp.core.pagination;

import java.util.Arrays;

/**
 * 정렬 정보를 의미합니다.
 */
public class Sort {
    private final String sortField;
    private final SortDirection sortDirection;

    private static final String DELIMITER = ",";
    private static final int EXPECTED_PARTS_COUNT = 2;

    private Sort(String sortField, SortDirection sortDirection) {
        this.sortField = sortField;
        this.sortDirection = sortDirection;
    }

    public static Sort fromByDefault(String sortSpecification) {

        String[] sortComponents = extractSortComponents(sortSpecification);

        if (sortComponents.length != EXPECTED_PARTS_COUNT) {
            throw new IllegalArgumentException("sort must be in the format of 'field, direction'");
        }

        return new Sort(sortComponents[0], SortDirection.from(sortComponents[1]));
    }

    private static String[] extractSortComponents(String sortSpecification) {
        return Arrays.stream(sortSpecification.split(DELIMITER)).map(String::trim).toArray(String[]::new);
    }

    public String getSortField() {
        return sortField;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public enum SortDirection {
        ASC, DESC;

        static SortDirection from(String order) {
            return SortDirection.valueOf(order.toUpperCase());
        }
    }
}
