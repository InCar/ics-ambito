package com.incarcloud.ambito.pojo;

import java.util.List;

/**
 * @author ThomasChan
 * @version 1.0
 * @description
 * @date 2018/12/25
 */
public class PageResult<T> {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private int totalElements;
    private List<T> content;
    private Page page;

    public PageResult(List<T> content, Page page) {
        this.content = content;
        this.page = page;
    }

    public int getCurrentPage() {
        return page.getCurrentPage();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return page.getCurrentSize();
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return page.getTotalSize();
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
