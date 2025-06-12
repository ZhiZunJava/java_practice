package com.itheima.dao;

import com.alibaba.druid.util.StringUtils;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 借阅记录数据访问对象
 * 负责借阅记录相关的数据库操作
 */
public class RecordDao {
    private static final Logger logger = Logger.getLogger(RecordDao.class.getName());
    private final QueryRunner queryRunner;
    
    // SQL常量定义
    private static final String SQL_SELECT_BASE = "SELECT * FROM record WHERE 1=1";
    private static final String SQL_INSERT_RECORD = "INSERT INTO record VALUES(?, ?, ?, ?, ?)";
    
    // 用户角色常量
    private static final String ROLE_CUSTOMER = "顾客";
    
    public RecordDao() {
        this.queryRunner = new QueryRunner(JDBCUtils.getDataSource());
    }
    
    /**
     * 查询借阅记录
     * @param bookName 图书名称（可为空）
     * @param user 当前用户对象
     * @return 借阅记录列表
     * @throws SQLException 数据库操作异常
     */
    public List<Record> searchRecord(String bookName, User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("用户对象不能为空");
        }
        
        try {
            QueryBuilder queryBuilder = new QueryBuilder();
            buildSearchConditions(queryBuilder, bookName, user);
            
            String sql = queryBuilder.getSql();
            Object[] params = queryBuilder.getParams().toArray();
            
            logger.info("执行借阅记录查询: " + sql);
            List<Record> records = queryRunner.query(sql, new BeanListHandler<>(Record.class), params);
            
            logger.info("查询到 " + records.size() + " 条借阅记录");
            return records;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "借阅记录查询失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 构建搜索条件
     */
    private void buildSearchConditions(QueryBuilder queryBuilder, String bookName, User user) {
        // 如果输入了书名，添加书名模糊查询条件
        if (!StringUtils.isEmpty(bookName)) {
            queryBuilder.addCondition(" AND bookname LIKE ?", "%" + bookName + "%");
        }
        
        // 如果是顾客角色，只能查看自己的借阅记录
        if (ROLE_CUSTOMER.equals(user.getRole())) {
            queryBuilder.addCondition(" AND borrower = ?", user.getName());
        }
    }
    
    /**
     * 添加图书借阅记录
     * @param record 借阅记录对象
     * @return 影响的行数
     * @throws SQLException 数据库操作异常
     */
    public int addRecord(Record record) throws SQLException {
        if (record == null) {
            throw new IllegalArgumentException("借阅记录对象不能为空");
        }
        
        validateRecord(record);
        
        try {
            Object[] params = {
                record.getId(), record.getBookname(),
                record.getBorrower(), record.getBorrowtime(), record.getRemandtime()
            };
            
            int result = queryRunner.update(SQL_INSERT_RECORD, params);
            
            if (result > 0) {
                logger.info("添加借阅记录成功 - 图书: " + record.getBookname() + 
                          ", 借阅者: " + record.getBorrower());
            }
            
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "添加借阅记录失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 验证借阅记录数据的有效性
     */
    private void validateRecord(Record record) {
        if (StringUtils.isEmpty(record.getBookname())) {
            throw new IllegalArgumentException("图书名称不能为空");
        }
        
        if (StringUtils.isEmpty(record.getBorrower())) {
            throw new IllegalArgumentException("借阅者不能为空");
        }
        
        if (StringUtils.isEmpty(record.getBorrowtime())) {
            throw new IllegalArgumentException("借阅时间不能为空");
        }
    }
    
    /**
     * 根据图书ID和借阅者查询借阅记录
     * @param bookId 图书ID
     * @param borrower 借阅者
     * @return 借阅记录对象，如果不存在返回null
     * @throws SQLException 数据库操作异常
     */
    public Record findRecordByBookAndBorrower(Integer bookId, String borrower) throws SQLException {
        if (bookId == null || StringUtils.isEmpty(borrower)) {
            return null;
        }
        
        try {
            String sql = "SELECT * FROM record WHERE book_id = ? AND borrower = ? AND remandtime IS NULL";
            List<Record> records = queryRunner.query(sql, new BeanListHandler<>(Record.class), bookId, borrower);
            
            return records.isEmpty() ? null : records.get(0);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "查询借阅记录失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 更新借阅记录的归还时间
     * @param recordId 记录ID
     * @param returnTime 归还时间
     * @return 影响的行数
     * @throws SQLException 数据库操作异常
     */
    public int updateReturnTime(Integer recordId, String returnTime) throws SQLException {
        if (recordId == null || StringUtils.isEmpty(returnTime)) {
            throw new IllegalArgumentException("记录ID和归还时间不能为空");
        }
        
        try {
            String sql = "UPDATE record SET remandtime = ? WHERE id = ?";
            int result = queryRunner.update(sql, returnTime, recordId);
            
            if (result > 0) {
                logger.info("更新归还时间成功，记录ID: " + recordId);
            }
            
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "更新归还时间失败: " + e.getMessage(), e);
            throw e;
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
