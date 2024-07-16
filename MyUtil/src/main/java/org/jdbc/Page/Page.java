package org.jdbc.Page;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
        private List<T> list;  //当前页列表数据，数据库查询得到
        private int pageNumber;  //当前页码，前端页面传递
        private int totalRecord;  //总记录数，数据库查询得到
        private int pageSize;    //每页显示条数，前端页面传递
        private int totalPage;   //总页数，计算得到
        private int index;      //当前页的起始索引，计算
        private String path;      //用来设置Servlet访问路径及method参数

        public Page() {
            super();
        }

        public Page(int pageNumber, int totalRecord, int pageSize) {
            super();
            this.pageNumber = pageNumber;
            this.totalRecord = totalRecord;
            this.pageSize = pageSize;
        }

        public List<T> getList() {
            return list;
        }
        public void setList(List<T> list) {
            this.list = list;
        }
        public int getPageNumber() {     //控制页码不能<1，也不能>totalPage
            if(pageNumber<1){
                pageNumber=1;
            }else if (getTotalPage()!=0&&pageNumber>getTotalPage()) {
                pageNumber=getTotalPage();
            }
            return pageNumber;
        }
        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }
        public int getTotalRecord() {
            return totalRecord;
        }
        public void setTotalRecord(int totalRecord) {
            this.totalRecord = totalRecord;
        }
        public int getPageSize() {
            return pageSize;
        }
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
        public int getTotalPage() {
             return  totalPage=(int) Math.ceil((double)getTotalRecord()/getPageSize());
        }
        public int getIndex() {
            return (getPageNumber()-1)*getPageSize();     //分页查询，在数据访问层一定会调用getIndex方法获得索引值
        }                                                 //而在getIndex方法中调用了getPageNumber方法，保证了页码在正常范围内

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }