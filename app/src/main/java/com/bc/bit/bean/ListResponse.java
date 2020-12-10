package com.bc.bit.bean;

import java.util.List;

/**
 * 解析列表网络请求
 */
public class ListResponse<T> extends BaseResponse{

    private List<T> data;
    private int currentPage;
    private boolean hasMore;
    private int pageSize;
    private int totalPage;
    private int totalSize;

    public List<T> getList() {
        return data;
    }

    public void setList(List<T> list) {
        this.data = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
