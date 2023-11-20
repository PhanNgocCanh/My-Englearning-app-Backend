package com.exerciseapp.myapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtil {

    public static Pageable of(int page, int size) {
        return PageRequest.of(page, size);
    }

    public static Pageable of(int page, int size, Map<String, String> sorts, boolean nativeQuery) {
        List<Sort.Order> orders = new ArrayList<>();
        sorts.forEach((k, v) -> orders.add(new Sort.Order(Sort.Direction.valueOf(v.toUpperCase()), k)));
        if (nativeQuery) {
            orders.add(new Sort.Order(Sort.Direction.DESC, "created_date"));
        } else {
            orders.add(new Sort.Order(Sort.Direction.DESC, "createdDate"));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }
}
