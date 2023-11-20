package com.exerciseapp.myapp.service.request;

import java.util.HashMap;
import java.util.Map;

public class SearchRequest {

    private int page = 0;

    private int size = 20;

    private Map<String, String> sorts = new HashMap<>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, String> getSorts() {
        return sorts;
    }

    public void setSorts(Map<String, String> sorts) {
        this.sorts = sorts;
    }
}
