package petalk.mvp.core.pagination;

/**
 * 정렬 정보를 의미합니다.
 */
public class Sort {
    private final String sortKey;
    private final Order order;

    public Sort(String sortKey, Order order) {
        this.sortKey = sortKey;
        this.order = order;
    }

    String getSortKey() {
        return sortKey;
    }

    Order getOrder() {
        return order;
    }

    public enum Order {
        ASC, DESC
    }
}
