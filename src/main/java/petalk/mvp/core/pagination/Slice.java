package petalk.mvp.core.pagination;

import java.util.List;

/**
 * Slice는 페이징된 데이터를 의미합니다.
 * Slice는 페이징된 데이터와 다음 페이지의 커서를 가지고 있습니다.
 * @param <T> 페이징된 데이터의 타입입니다.
 */
public interface Slice<T> {

    List<T> getContents();
    boolean hasNext();
    int getSize();
    Sort getSort();
    Cursor getNextCursor();

}
