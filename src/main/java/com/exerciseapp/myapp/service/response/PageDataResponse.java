package com.exerciseapp.myapp.service.response;

import java.util.List;
import org.springframework.data.domain.Page;

public class PageDataResponse<E> {

    private long totalElements;

    private int page;

    private int size;

    private List<E> data;

    public static <E> PageDataResponse<E> of(int totalElements, int page, int size, List<E> data) {
        PageDataResponse<E> rs = new PageDataResponse<>();
        rs.setTotalElements(totalElements);
        rs.setPage(page);
        rs.setSize(size);
        rs.setData(data);

        return rs;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

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

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
