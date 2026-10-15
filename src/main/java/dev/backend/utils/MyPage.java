package dev.backend.utils;

import java.util.List;

public class MyPage<T> {
    private Integer totalElements;
    private Integer size;
    private Integer currentPage;
    private List<T> content;
    //TODO: all variables: totalElements, size, (currentPage?) export to separate class - Pageable

    public MyPage(Integer totalElements, Integer size, Integer currentPage, List<T> content) {
        this.totalElements = totalElements;
        this.size = size;
        this.currentPage = currentPage;
        this.content = content;
    }

    public Integer getTotalPages() {
        return Math.ceilDiv(totalElements, size);
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
}
