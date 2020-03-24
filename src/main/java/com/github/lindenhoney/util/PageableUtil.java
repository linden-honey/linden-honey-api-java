package com.github.lindenhoney.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableUtil {
    public static Pageable createPageable(
            int limit,
            int offset,
            String sortBy,
            String sortOrder
    ) {
        return PageRequest.of(
                offset / limit,
                limit,
                Sort.Direction.fromString(sortOrder),
                sortBy
        );
    }
}
