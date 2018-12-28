package com.incarcloud.ics.ambito.pojo;

public class Page {
  
    private int currentSize;  
    private int offSize;  
    private int totalSize;  
    private int totalPages;  
    private int currentPage;


    /**
     * 分页构造函数
     * @param currentPage
     * @param pageSize
     */
    public Page(int currentPage, int pageSize) {
        super();
        if(currentPage < 1 || pageSize < 1){
            throw new IllegalArgumentException("invalid pageNo or pageSize");
        }
        this.offSize = (currentPage - 1) * pageSize;
        this.currentSize = pageSize;
        this.currentPage = currentPage;
    }


    public boolean isValidPage(){
        return currentPage > 0;
    }


    public int getCurrentSize() {
        return currentSize;  
    }  
  
    public void setCurrentSize(int currentSize) {  
        this.currentSize = currentSize;  
    }  
  
    public int getOffSize() {  
        return offSize;  
    }  
  
    public void setOffSize(int offSize) {  
        this.offSize = offSize;  
    }  
  
    public int getTotalSize() {  
        return totalSize;  
    }  
  
    public void setTotalSize(int totalSize) {  
        this.totalSize = totalSize;  
    }  
  
    public int getTotalPages() {  
        return this.getTotalSize() % this.getCurrentSize() == 0
                ? this.getTotalSize() / this.getCurrentSize() :
                 (this.getTotalSize() / this.getCurrentSize() + 1);
    }  
  
    public void setTotalPages(int totalPages) {  
        this.totalPages = totalPages;  
    }  
  
    public int getCurrentPage() {  
        return currentPage;  
    }  
  
    public void setCurrentPage(int currentPage) {  
        this.currentPage = currentPage;  
    }

    @Override
    public String toString() {  
        return "Page [currentSize=" + currentSize + ", offSize=" + offSize  
                + ", totalSize=" + totalSize + ", totalPages=" + totalPages  
                + ", currentPage=" + currentPage + ", totalSizeNew="  
                 + "]";
    }  
  
      
      
      
      
}  