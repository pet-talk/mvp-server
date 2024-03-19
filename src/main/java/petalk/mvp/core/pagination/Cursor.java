package petalk.mvp.core.pagination;


/**
 * 커서를 의미합니다.
 *
 * 커서는 페이지의 위치를 나타내는 값입니다.
 */
public class Cursor {

    // 커서 값은 json 형태로 저장됩니다.
    private final String cursor;

    public Cursor(String cursor) {
        this.cursor = cursor;
    }

    public String getCursor() {
        return cursor;
    }
}
