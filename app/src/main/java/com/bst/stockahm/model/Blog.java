package com.bst.stockahm.model;

/**
 * Created by AK on 16-Jun-17.
 */

public class Blog {
    String blog_date;
    String blog_detail;
    String blog_status;

    public Blog(String blog_date, String blog_detail, String blog_status) {
        this.blog_date = blog_date;
        this.blog_detail = blog_detail;
        this.blog_status = blog_status;
    }

    public String getBlog_status() {
        return blog_status;
    }

    public void setBlog_status(String blog_status) {
        this.blog_status = blog_status;
    }

    public String getBlog_detail() {
        return blog_detail;
    }

    public void setBlog_detail(String blog_detail) {
        this.blog_detail = blog_detail;
    }

    public String getBlog_date() {
        return blog_date;
    }

    public void setBlog_date(String blog_date) {
        this.blog_date = blog_date;
    }




}
