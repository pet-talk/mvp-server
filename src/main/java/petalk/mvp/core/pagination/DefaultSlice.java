package petalk.mvp.core.pagination;

import java.util.Collections;
import java.util.List;

/**
 * DefaultSlice는 Slice의 기본 구현체입니다.
 * @param <T>
 */
public class DefaultSlice<T> implements Slice<T> {

    private final List<T> contents;
    private final boolean hasNext;
    private final int size;
    private final Sort sort;
    private final String nextCursor;

    public DefaultSlice(List<T> contents, boolean hasNext, int size, Sort sort, String nextCursor) {
        this.contents = Collections.unmodifiableList(contents);
        this.hasNext = hasNext;
        this.size = size;
        this.sort = sort;
        this.nextCursor = nextCursor;
    }
    @Override
    public List<T> getContents() {
        return contents;
    }
    @Override
    public boolean hasNext() {
        return hasNext;
    }
    @Override
    public int getSize() {
        return size;
    }
    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public String getNextCursor() {
        return nextCursor;
    }
}
