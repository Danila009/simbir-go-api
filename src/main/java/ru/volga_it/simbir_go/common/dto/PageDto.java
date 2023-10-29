package ru.volga_it.simbir_go.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageDto<T> {

    private final Integer totalPages;
    private final Long totalElements;
    private final List<T> content;

    public PageDto(Page<T> page) {
        totalPages = page.getTotalPages();
        totalElements = page.getTotalElements();
        content = page.getContent();
    }
}
