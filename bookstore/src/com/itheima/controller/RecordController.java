package com.itheima.controller;

import com.itheima.dao.RecordDao;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.enums.Constants;
import com.itheima.utils.MessageUtils;
import com.itheima.utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 借阅记录控制器
 * 负责借阅记录界面的逻辑处理
 */
public class RecordController extends BaseController {
    private static final Logger logger = Logger.getLogger(RecordController.class.getName());
    
    // FXML组件
    @FXML
    private TextField recBookName;      // 借阅记录查询文本框
    @FXML
    private TableView<Record> tableView; // 借阅记录列表表格
    
    private final RecordDao recordDao = new RecordDao();
    
    /**
     * 查询借阅记录按钮事件处理
     */
    @FXML
    void showRecords(ActionEvent event) {
        initFrame();
    }
    
    /**
     * 初始化界面并加载数据
     */
    @Override
    public void initFrame() {
        try {
            loadRecords();
            setupTableColumns();
            logger.info("借阅记录界面初始化完成");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "借阅记录界面初始化失败: " + e.getMessage(), e);
            MessageUtils.showError("加载借阅记录失败，请稍后重试！");
        }
    }
    
    /**
     * 加载借阅记录数据
     */
    private void loadRecords() throws SQLException {
        String bookName = getSearchBookName();
        User currentUser = getCurrentUser();
        
        if (currentUser == null) {
            MessageUtils.showError("用户会话已过期，请重新登录！");
            return;
        }
        
        List<Record> records = recordDao.searchRecord(bookName, currentUser);
        ObservableList<Record> recordList = FXCollections.observableArrayList(records);
        tableView.setItems(recordList);
        
        logger.info("成功加载 " + records.size() + " 条借阅记录");
    }
    
    /**
     * 设置表格列的数据绑定
     */
    private void setupTableColumns() {
        ObservableList<TableColumn<Record, ?>> columns = tableView.getColumns();
        
        for (TableColumn<Record, ?> column : columns) {
            String columnName = column.getText();
            setupColumnCellValueFactory(column, columnName);
        }
    }
    
    /**
     * 设置列的单元格值工厂
     */
    @SuppressWarnings("unchecked")
    private void setupColumnCellValueFactory(TableColumn<Record, ?> column, String columnName) {
        switch (columnName) {
            case "编号":
                ((TableColumn<Record, Object>) column).setCellValueFactory(new PropertyValueFactory<>("id"));
                break;
            case "书名":
                ((TableColumn<Record, Object>) column).setCellValueFactory(new PropertyValueFactory<>("bookname"));
                break;
            case "借阅者":
                ((TableColumn<Record, Object>) column).setCellValueFactory(new PropertyValueFactory<>("borrower"));
                break;
            case "借阅时间":
                ((TableColumn<Record, Object>) column).setCellValueFactory(new PropertyValueFactory<>("borrowtime"));
                break;
            case "归还时间":
                ((TableColumn<Record, Object>) column).setCellValueFactory(new PropertyValueFactory<>("remandtime"));
                break;
            default:
                logger.warning("未知的表格列名: " + columnName);
                break;
        }
    }
    
    /**
     * 获取搜索的图书名称
     */
    private String getSearchBookName() {
        String bookName = recBookName.getText();
        return bookName != null ? bookName.trim() : "";
    }
    
    /**
     * 获取当前登录用户
     */
    private User getCurrentUser() {
        return Session.getUser();
    }
}
