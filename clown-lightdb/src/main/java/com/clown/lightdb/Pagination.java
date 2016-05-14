package com.clown.lightdb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by len.li on 22/3/2016.
 * 分页对像
 */
public class Pagination<T>  {

    private Integer currentPage = 1;
    private Integer pageSize = 15;
    private Integer total=0;
    private List<T> list = new ArrayList<T>();

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}
