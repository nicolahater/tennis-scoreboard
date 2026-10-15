package dev.backend.utils;

import java.util.List;

public class MyPage<T> {
    private Long totalElements;
    private Integer size;
    private Integer currentPage;
    private List<T> content;
    //TODO: all variables: totalElements, size, (currentPage?) export to separate class - Pageable

    public MyPage(List<T> content, Integer currentPage, Long totalElements, Integer size) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.size = size;
    }

    public Long getTotalPages() {
        return Math.ceilDiv(totalElements, size);
    }

    public List<T> getContent() {
        return content;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
}
