package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Chunk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface GenericMapper<D, E> {

    D toDomain(E entity);

    List<D> toDomain(List<E> entity);

    E toEntity(D domain);

    List<E> toEntity(List<D> domain);

    default Chunk<D> toDomain(Page<E> page) {
        if (page == null) {
            return null;
        }
        final Optional<Sort.Order> order = page
                .getSort()
                .stream()
                .findFirst();
        final String sortBy = order.map(Sort.Order::getProperty)
                .orElse(null);
        final String sortOrder = order.map(Sort.Order::getDirection)
                .map(direction -> direction.name().toLowerCase())
                .orElse(Chunk.DEFAULT_SORT_ORDER);
        return Chunk.<D>builder()
                .data(toDomain(page.getContent()))
                .limit(page.getSize())
                .offset(page.getPageable().getOffset())
                .total(page.getTotalElements())
                .sortBy(sortBy)
                .sortOrder(sortOrder)
                .build();
    }
}
