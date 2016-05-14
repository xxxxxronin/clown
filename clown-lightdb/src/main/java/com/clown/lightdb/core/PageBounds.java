package com.clown.lightdb.core;

/**
 * Created by len.li on 2016/4/12.
 * 自动分页识别类
 */
public class PageBounds {
    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = Integer.MAX_VALUE;
    public static final PageBounds DEFAULT = new PageBounds();

    private int offset;
    private int limit;

    public PageBounds() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    public PageBounds(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }
}
