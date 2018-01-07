package com.aptech.itblog.model;

import java.util.List;

public class AdminQuery {
    private List<Integer> range;
    private List<String> sort;
    private Object filter;

    public AdminQuery() {
    }

    public AdminQuery(List<Integer> range, List<String> sort, Object filter) {
        this.range = range;
        this.sort = sort;
        this.filter = filter;
    }

    public List<Integer> getRange() {
        return range;
    }

    public void setRange(List<Integer> range) {
        this.range = range;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public Object getFilter() {
        return filter;
    }

    public void setFilter(Object filter) {
        this.filter = filter;
    }
}
