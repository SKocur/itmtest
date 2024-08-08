package com.itmagination.itmtest.common;

import java.util.List;

public record SearchResult<T>(
        int totalPages,
        long totalElements,
        List<T> content
) {
}
