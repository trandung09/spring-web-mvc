package com.tvd.petcare.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableUtils {

    public static Pageable convertPageable(int page, int size, String sort) {

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        String sortDirection = sortParams[1];

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sortBy = Sort.by(direction, sortField);

        return PageRequest.of(page, size, sortBy);
    }

    public static Pageable createDefaultPageable() {

        Sort.Direction direction = Sort.Direction.fromString("asc");
        Sort sortBy = Sort.by(direction, "id");

        return PageRequest.of(0, 10, sortBy);
    }
}
