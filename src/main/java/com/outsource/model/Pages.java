package com.outsource.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author chuanchen
 */
public class Pages<T> implements Serializable{
    private static final long serialVersionUID = -1L;

    private Integer pageNumber;
    private Integer totalCount;
    private List<T> pageList;

    public Pages(int pageNumber, int totalCount, List<T> pageList){
        this.pageNumber = pageNumber;
        this.totalCount = totalCount;
        this.pageList = pageList;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    @Override
    public String toString() {
        return "Pages{" +
                "pageNumber=" + pageNumber +
                ", totalCount=" + totalCount +
                ", pageList=" + pageList +
                '}';
    }
}
