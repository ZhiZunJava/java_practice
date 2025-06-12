package com.itheima.domain;

public class Book {
    //图书编号
    private Integer id;
    //图书名称
    private String bookname;
    //图书作者
    private String author;
    //图书简介
    private String des;
    //图书状态
    private String state;
    //借阅人
    private String borrower;
    //图书借阅时间
    private String borrowTime;

    public Book() {
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public Book(Integer id, String bookname, String author, String state, String des) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.des = des;
        this.state = state;
    }

    public Book(Integer id, String bookname, String author, String des, String state, String borrower, String borrowTime) {
        this.id = id;
        this.bookname = bookname;
        this.author = author;
        this.des = des;
        this.state = state;
        this.borrower = borrower;
        this.borrowTime = borrowTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
