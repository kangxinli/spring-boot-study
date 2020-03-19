package com.sample.app.utils;

import java.util.List;

public class ListSub<T> {  
    /**  
     * 当前页面  
     */  
    private int page = 1;  
  
    /**  
     * 显示多少行  
     */  
    private int rows = 15;  
  
    /**  
     * 总记录条数  
     */  
    private int total;  
    
    public ListSub(int page, int rows) {
    	this.page = page;
    	this.rows = rows;
    }
  
    /**  
     * @return the page  
     */  
    public int getPage() {  
        return page;  
    }  
  
    /**  
     * @param page the page to set  
     */  
    public void setPage(int page) {  
        this.page = page;  
    }  
  
    /**  
     * @return the rows  
     */  
    public int getRows() {  
        return rows;  
    }  
  
    /**  
     * @param rows the rows to set  
     */  
    public void setRows(int rows) {  
        this.rows = rows;  
    }  
  
    /**  
     * @return the total  
     */  
    public int getTotal() {  
        return total;  
    }  
  
    /**  
     * @param total the total to set  
     */  
    public void setTotal(int total) {  
        this.total = total;  
    }  
  
    /**  
     * 对list集合进行分页处理  
     *   
     * @return  
     */  
    public List<T> ListSplit(List<T> list) {  
    	if (list == null) {
        	return null;
        }
        
        total = list.size();  
        
        int start = rows * (page - 1);
        if (start < 0 || start >= total) {
        	return null;
        }
        
        int end = (rows * page) > total ? total : (rows * page);
        
        List<T> newList = null;  
        newList = list.subList(start, end);  
        return newList;  
    }  
} 
