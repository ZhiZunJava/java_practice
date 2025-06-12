package com.itheima.dao;

import com.alibaba.druid.util.StringUtils;
import com.itheima.domain.Book;
import com.itheima.utils.JDBCUtils;
import com.itheima.utils.Session;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 图书数据访问对象
 * 负责图书相关的数据库操作
 */
public class BookDao {
    private static final Logger logger = Logger.getLogger(BookDao.class.getName());
    private final QueryRunner queryRunner;
    
    // 常量定义
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // 图书状态枚举
    public enum BookState {
        AVAILABLE("可借阅"),
        BORROWED("借阅中"),
        OFFLINE("已下架");
        
        private final String description;
        
        BookState(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
        
        public static BookState fromDescription(String description) {
            for (BookState state : values()) {
                if (state.description.equals(description)) {
                    return state;
                }
            }
            return null;
        }
    }
    
    // 用户角色枚举
    public enum UserRole {
        CUSTOMER("顾客"),
        ADMIN("管理员");
        
        private final String description;
        
        UserRole(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // SQL常量定义
    private static final String SQL_SELECT_BASE = "SELECT * FROM book WHERE 1=1";
    private static final String SQL_INSERT_BOOK = "INSERT INTO book(bookname, author, state, des) VALUES(?, ?, ?, ?)";
    private static final String SQL_INSERT_BOOK_WITH_BORROWER = "INSERT INTO book(bookname, author, state, des, borrower, borrowtime) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_BOOK = "UPDATE book SET bookname = ?, author = ?, state = ?, des = ? WHERE id = ?";
    private static final String SQL_UPDATE_BOOK_LEND_RETURN = "UPDATE book SET state = ?, borrower = ?, borrowtime = ? WHERE id = ?";
    
    public BookDao() {
        this.queryRunner = new QueryRunner(JDBCUtils.getDataSource());
    }
    
    /**
     * 查询图书
     * @param book 查询条件对象
     * @return 图书列表
     * @throws SQLException 数据库操作异常
     */
    public List<Book> searchBook(Book book) throws SQLException {
        if (book == null) {
            book = new Book();
        }
        
        try {
            QueryBuilder queryBuilder = new QueryBuilder();
            buildSearchQuery(queryBuilder, book);
            
            String sql = queryBuilder.getSql();
            Object[] params = queryBuilder.getParams().toArray();
            
            logger.info("执行图书查询: " + sql);
            return queryRunner.query(sql, new BeanListHandler<>(Book.class), params);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "图书查询失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 构建搜索查询条件
     */
    private void buildSearchQuery(QueryBuilder queryBuilder, Book book) {
        // 添加状态条件
        if (!StringUtils.isEmpty(book.getState())) {
            queryBuilder.addCondition(" AND state = ?", book.getState());
            
            // 如果是顾客角色查询借阅中的图书，添加借阅者条件
            if (BookState.BORROWED.getDescription().equals(book.getState()) && 
                UserRole.CUSTOMER.getDescription().equals(Session.getUser().getRole())) {
                queryBuilder.addCondition(" AND borrower = ?", Session.getUser().getName());
            }
        }
        
        // 添加图书名称条件
        if (!StringUtils.isEmpty(book.getBookname())) {
            queryBuilder.addCondition(" AND bookname LIKE ?", "%" + book.getBookname() + "%");
        }
        
        // 添加图书编号条件
        if (book.getId() != null) {
            queryBuilder.addCondition(" AND id = ?", book.getId().toString());
        }
    }
    
    /**
     * 添加或修改图书
     * @param book 图书对象
     * @return 影响的行数
     * @throws SQLException 数据库操作异常
     */
    public int addOrEditBook(Book book) throws SQLException {
        if (book == null) {
            throw new IllegalArgumentException("图书对象不能为空");
        }
        
        validateBookData(book);
        
        try {
            if (book.getId() == null) {
                return addBook(book);
            } else {
                return updateBook(book);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "图书添加或修改失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 添加新图书
     */
    private int addBook(Book book) throws SQLException {
        if (BookState.BORROWED.getDescription().equals(book.getState())) {
            // 如果新增图书状态为借阅中，设置借阅人为当前登录用户
            Object[] params = {
                book.getBookname(), book.getAuthor(), book.getState(),
                book.getDes(), Session.getUser().getName(), 
                LocalDateTime.now().format(DATE_FORMATTER)
            };
            int result = queryRunner.update(SQL_INSERT_BOOK_WITH_BORROWER, params);
            logger.info("添加图书成功（借阅中）: " + book.getBookname());
            return result;
        } else {
            Object[] params = {book.getBookname(), book.getAuthor(), book.getState(), book.getDes()};
            int result = queryRunner.update(SQL_INSERT_BOOK, params);
            logger.info("添加图书成功: " + book.getBookname());
            return result;
        }
    }
    
    /**
     * 更新图书信息
     */
    private int updateBook(Book book) throws SQLException {
        Object[] params = {
            book.getBookname(), book.getAuthor(), book.getState(), 
            book.getDes(), book.getId()
        };
        int result = queryRunner.update(SQL_UPDATE_BOOK, params);
        logger.info("更新图书成功，ID: " + book.getId());
        return result;
    }
    
    /**
     * 借阅或归还图书
     * @param book 图书对象
     * @return 影响的行数
     * @throws SQLException 数据库操作异常
     */
    public int lendOrReturnBook(Book book) throws SQLException {
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("图书对象或图书ID不能为空");
        }
        
        try {
            Object[] params = {
                book.getState(), book.getBorrower(), book.getBorrowTime(), book.getId()
            };
            int result = queryRunner.update(SQL_UPDATE_BOOK_LEND_RETURN, params);
            
            String action = BookState.BORROWED.getDescription().equals(book.getState()) ? "借阅" : "归还";
            logger.info("图书" + action + "成功，ID: " + book.getId());
            
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "图书借阅或归还失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 验证图书数据的有效性
     */
    private void validateBookData(Book book) {
        if (StringUtils.isEmpty(book.getBookname())) {
            throw new IllegalArgumentException("图书名称不能为空");
        }
        
        if (StringUtils.isEmpty(book.getAuthor())) {
            throw new IllegalArgumentException("作者不能为空");
        }
        
        if (StringUtils.isEmpty(book.getState())) {
            throw new IllegalArgumentException("图书状态不能为空");
        }
        
        // 验证状态是否有效
        if (BookState.fromDescription(book.getState()) == null) {
            throw new IllegalArgumentException("无效的图书状态: " + book.getState());
        }
    }
    
    /**
     * 查询构建器内部类
     */
    private static class QueryBuilder {
        private final StringBuilder sql;
        private final List<String> params;
        
        public QueryBuilder() {
            this.sql = new StringBuilder(SQL_SELECT_BASE);
            this.params = new ArrayList<>();
        }
        
        public void addCondition(String condition, String param) {
            sql.append(condition);
            params.add(param);
        }
        
        public String getSql() {
            return sql.toString();
        }
        
        public List<String> getParams() {
            return params;
        }
    }
}