package com.itheima.controller;

import com.alibaba.druid.util.StringUtils;
import com.itheima.dao.BookDao;
import com.itheima.dao.RecordDao;
import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class BookController extends BaseController{
    @FXML
    private Pane addOrEditPane;          //添加或修改面板
    @FXML
    private TableView<Book> tableView;   //图书列表表格
    @FXML
    private TextField des;               //图书简介文本框
    @FXML
    private ComboBox<String> seachState; //图书查询下拉框
    @FXML
    private TextField author;            //作者文本框
    @FXML
    private TextField bookVo;            //图书查询文本框
    @FXML
    private ComboBox<String> editState;  //图书添加或修改下拉框
    @FXML
    private TextField bookname;          //图书名称文本框
    @FXML
    private Label bid;                   //图书编号标签
    @FXML
    private Pane lendPane;               //图书借阅或归还面板
    @FXML
    private TextField lendAuthor;        //借阅图书作者文本框
    @FXML
    private TextField lendDes;           //借阅图书简介文本框
    @FXML
    private TextField lendBookname;      //借阅图书名称文本框
    @FXML
    private Label lendBid;               //借阅图书编号标签
    @FXML
    private Button lendBookBt;           //借阅按钮
    @FXML
    private Button returnBookBt;         //归还按钮
    private BookDao bookDao=new BookDao();
    private void toSeach() {
        try {
            //获取图书查询下拉框的选中项
            String state = seachState.getSelectionModel().getSelectedItem();
            Book book = new Book();
            book.setState(state);
            //设置查询图书的名称
            book.setBookname(bookVo.getText());
            //如果当前搜索图书的状态为借阅中，并且不是管理员角色
            if("借阅中".equals(state)&&"顾客".equals(Session.getUser().getRole())){
                //设置借阅按钮为禁用状态
                lendBookBt.setDisable(true);
                //设置归还按钮为可用状态
                returnBookBt.setDisable(false);
            }else {
                //设置借阅按钮为可用状态
                lendBookBt.setDisable(false);
                //设置借阅按钮为禁用状态
                returnBookBt.setDisable(true);
            }
            //获取图书列表表格的素有列
            ObservableList<TableColumn<Book, ?>> columns = tableView.getColumns();
            boolean containsBorrowerColumn = false;
            //遍历表格的所有列
            Iterator<TableColumn<Book, ?>> iterator = columns.iterator();
            while (iterator.hasNext()) {
                TableColumn<Book, ?> column = iterator.next();
                //获取列的标题的文本
                String cname = column.getText();
                //根据列的标题，将当前列和和对应的属性绑定
                switch (cname) {
                    case "编号":
                        column.setCellValueFactory(new PropertyValueFactory<>("id"));
                        break;
                    case "书名":
                        column.setCellValueFactory(new PropertyValueFactory<>("bookname"));
                        break;
                    case "作者":
                        column.setCellValueFactory(new PropertyValueFactory<>("author"));
                        break;
                    case "简介":
                        column.setCellValueFactory(new PropertyValueFactory<>("des"));
                        break;
                }
                //如果当前查询的为借阅中的图书，并且当前列的标题为借阅者，则将当前列绑定borrower属性
                if ("借阅中".equals(state) && "借阅者".equals(cname)) {
                    containsBorrowerColumn = true;
                    column.setCellValueFactory(new PropertyValueFactory<>("borrower"));
                }
                //如果当前查询的不是借阅中的图书，并且遍历到标题为借阅者的列，则移出该列
                if (!"借阅中".equals(state) && "借阅者".equals(cname)) {
                    iterator.remove();
                }
            }
            //如果查询的图书状态为借阅中，并且图书列表表格中不存在借阅者列，则添加一列
            if (!containsBorrowerColumn && "借阅中".equals(state)) {
                // 创建表格列，并指定表中要显示的数据类型
                TableColumn<Book, String> column = new TableColumn<>("借阅者");
                tableView.getColumns().add(column);
                column.setCellValueFactory(new PropertyValueFactory<>("borrower"));
            }
            //根据图书状态和搜索框的内容查询图书
            List<Book> books = bookDao.searchBook(book);
            ObservableList<Book> blist = FXCollections.observableArrayList(books);
            //设置图书列表表格中的数据
            tableView.setItems(blist);
            if (tableView.getSelectionModel() != null) {
                //为表格中每行注册事件，当表格中的行被选中时触发
                tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        //获取当前登录的永固
                        User user = Session.getUser();
                        // 如果为管理员角色，则将选中行中的图书信息设置到添加或修改面板中的组件中
                        if ("管理员".equals(user.getRole())) {
                            bookname.setText(newSelection.getBookname());
                            author.setText(newSelection.getAuthor());
                            editState.getSelectionModel().select(newSelection.getState());
                            des.setText(newSelection.getDes());
                            bid.setText(newSelection.getId() + "");
                        }else {
                            //如果不是管理员角色，则将选中行中的图书信息设置到图书借阅面板中的组件中
                            lendBookname.setText(newSelection.getBookname());
                            lendAuthor.setText(newSelection.getAuthor());
                            lendDes.setText(newSelection.getDes());
                            lendBid.setText(newSelection.getId() + "");
                        }
                    }
                });
            }
            //重置组件中的信息
            resetBookInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 重置图书列表下面板中组件的文本
     */
    private void resetBookInfo() {
        bookname.setText("");
        author.setText("");
        editState.getSelectionModel().select(0);
        des.setText("");
        bid.setText("");
        lendBookname.setText("");
        lendAuthor.setText("");
        lendDes.setText("");
        lendBid.setText("");
    }
    /**
     * 界面初始化
     */
    @Override
    public void initFrame() {
        //管理员角色对应的图书搜索下拉框列表
        ObservableList adminState = FXCollections.observableArrayList("可借阅", "借阅中", "已下架");
        //顾客角色对应的图书搜索下拉框列表
        ObservableList otherState = FXCollections.observableArrayList("可借阅", "借阅中");
        //获取当前登录的用户信息
        User user = Session.getUser();
        //如果登录的为管理员
        if ("管理员".equals(user.getRole())) {
            //设置图书搜索下拉框列表
            seachState.setItems(adminState);
            //设置图书添加或修改面板中的下拉框列表
            editState.setItems(adminState);
            //设置图书添加或修改面板为可见
            addOrEditPane.setVisible(true);
            //设置图书借阅面板为不可见
            lendPane.setVisible(false);
        } else {
            //设置图书搜索下拉框列表
            seachState.setItems(otherState);
            //设置图书添加或修改面板为不可见
            addOrEditPane.setVisible(false);
            //设置图书借阅面板为可见
            lendPane.setVisible(true);
        }
        //设置下拉框的默认选中项
        seachState.getSelectionModel().select(0);
        editState.getSelectionModel().select(0);
        //查询图书
        toSeach();
    }
    /**
     *查询图书
     */
    @FXML
    public void searchBooks(ActionEvent actionEvent) {
        toSeach();
    }

    //创建消息提示框
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    /**
     * 获取图书添加\修改面板中组件的文本内容
     */
    private Book getBookInto() {
        String i = bid.getText();
        Integer id = null;
        if (!i.isEmpty()) {
            id = Integer.valueOf(i);
        }
        String bn = bookname.getText();
        String au = author.getText();
        String st = editState.getSelectionModel().getSelectedItem();
        String d = des.getText();
        Book book = new Book(id, bn, au, st, d);
        return book;
    }
    /**
     *添加图书
     */
    @FXML
    public void addBook(ActionEvent actionEvent) {
        try {
            //获取需要添加的图书信息
            Book book = getBookInto();
            if (book.getBookname().isEmpty() || book.getAuthor().isEmpty()) {
                alert.setContentText("书名和作者为必填项！");
            } else {
                book.setId(null);
                //添加图书
                bookDao.addOrEditBook(book);
                toSeach();
                alert.setContentText("添加成功！");
            }
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     *修改图书
     */
    @FXML
    public void editBook(ActionEvent actionEvent) {
        try {
            //获取编辑后的图书信息
            Book book = getBookInto();
            if (book.getId() == null) {
                alert.setContentText("修改图书需要先选中图书列表中的具体图书！");
            } else {
                Book b = new Book();
                b.setId(book.getId());
                //根据需要修改的图书id查询图书信息
                List<Book> books = bookDao.searchBook(b);
                //如果当前图书的借阅中，则不允许修改
                if ("借阅中".equals(books.get(0).getState())) {
                    alert.setContentText("借阅中的图书不能被修改！");
                    alert.show();
                    return;
                }
                if (book.getBookname().isEmpty() || book.getAuthor().isEmpty()) {
                    alert.setContentText("书名和作者为必填项！");
                } else {
                    //修改图书信息
                    bookDao.addOrEditBook(book);
                    toSeach();
                    alert.setContentText("修改成功！");
                }
            }
            alert.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //指定格式化日期和时间时的格式
    DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     *借阅图书
     */
    @FXML
    public void lendBook(ActionEvent actionEvent) {
        //获取当前图书编号标签的文本内容
        String bid = lendBid.getText();
        if(StringUtils.isEmpty(bid)){
            alert.setContentText("请先选中对应的图书！");
            alert.show();
            return;
        }
        Book book = new Book();
        book.setId(Integer.valueOf(bid));
        try {
            //根据图书的编号查询图书信息
            List<Book> books = bookDao.searchBook(book);
            book= books.get(0);
            //设置图书的状态为借阅中
            book.setState("借阅中");
            //设置借阅者为当前登录的用户
            book.setBorrower(Session.getUser().getName());
            //设置图书的借阅时间
            book.setBorrowTime(LocalDateTime.now().format(fm));
            //借阅图书
            int i = bookDao.lendOrReturnBook(book);
            if(i==1){
                alert.setContentText("借阅成功！");
                toSeach();
            }else {
                alert.setContentText("借阅失败！");
            }
            alert.show();
        } catch (SQLException e) {
            alert.setContentText("借阅失败！");
            alert.show();
            throw new RuntimeException(e);
        }
    }
    private RecordDao recordDao=new RecordDao();
    /**
     *归还图书
     */
    @FXML
    public void returnBook(ActionEvent actionEvent) {
        //获取当前图书编号标签的文本内容
        String bid = lendBid.getText();
        if(StringUtils.isEmpty(bid)){
            alert.setContentText("请先选中对应的图书！");
            alert.show();
            return;
        }
        Book book = new Book();
        book.setId(Integer.valueOf(bid));
        try {
            //根据图书的编号查询图书信息
            List<Book> books = bookDao.searchBook(book);
            book= books.get(0);
            //根据图书信息和当前时间创建图书借阅记录
            Record record = new Record(null,book.getBookname(), book.getBorrower(), book.getBorrowTime(),LocalDateTime.now().format(fm));
            //修改归还后图书的状态
            book.setState("可借阅");
            //修改归还后图书的借阅者
            book.setBorrower(null);
            //修改归还后图书的借阅时间
            book.setBorrowTime(null);
            //归还图书
            int b = bookDao.lendOrReturnBook(book);
            //添加图书借阅记录
            int c =recordDao.addRecord(record);
            if(b==1&&c==1){
                alert.setContentText("归还成功！");
                toSeach();
            }else {
                alert.setContentText("归还失败！");
            }
            alert.show();
        } catch (SQLException e) {
            alert.setContentText("归还失败！");
            alert.show();
            throw new RuntimeException(e);
        }
    }
}
