package com.itheima.domain;

public class Record {
    //借阅记录编号
    private Integer id;
    //借阅图书名称
    private String bookname;
    //借阅者
    private String borrower;
    //借阅时间
    private String borrowtime;
    //归还时间
    private String remandtime;

    public Record() {
    }

    public Record(Integer id, String bookname, String borrower, String borrowtime, String remandtime) {
        this.id = id;
        this.bookname = bookname;
        this.borrower = borrower;
        this.borrowtime = borrowtime;
        this.remandtime = remandtime;
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

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(String borrowtime) {
        this.borrowtime = borrowtime;
    }

    public String getRemandtime() {
        return remandtime;
    }

    public void setRemandtime(String remandtime) {
        this.remandtime = remandtime;
    }
}
