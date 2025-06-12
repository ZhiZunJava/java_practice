package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 用户数据访问对象
 * 负责用户相关的数据库操作
 */
public class UserDao {
    private static final Logger logger = Logger.getLogger(UserDao.class.getName());
    private final QueryRunner queryRunner;
    
    // SQL常量定义
    private static final String SQL_LOGIN = "SELECT * FROM user WHERE name = ? AND password = ? AND role = ?";
    private static final String SQL_UPDATE_USER = "UPDATE user SET password = ?, tel = ?, gender = ? WHERE id = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM user WHERE name = ?";
    
    public UserDao() {
        this.queryRunner = new QueryRunner(JDBCUtils.getDataSource());
    }
    
    /**
     * 用户登录验证
     * @param user 包含用户名、密码和角色信息的用户对象
     * @return 登录成功返回用户对象，失败返回null
     * @throws SQLException 数据库操作异常
     */
    public User login(User user) throws SQLException {
        if (user == null || user.getName() == null || user.getPassword() == null || user.getRole() == null) {
            logger.warning("登录参数不完整");
            return null;
        }
        
        try {
            User result = queryRunner.query(SQL_LOGIN, new BeanHandler<>(User.class), 
                                          user.getName(), user.getPassword(), user.getRole());
            if (result != null) {
                logger.info("用户 " + user.getName() + " 登录成功");
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "用户登录失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 编辑用户信息
     * @param user 要编辑的用户对象
     * @return 影响的行数
     * @throws SQLException 数据库操作异常
     */
    public int editUser(User user) throws SQLException {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("用户对象或用户ID不能为空");
        }
        
        try {
            Object[] params = {user.getPassword(), user.getTel(), user.getGender(), user.getId()};
            int result = queryRunner.update(SQL_UPDATE_USER, params);
            if (result > 0) {
                logger.info("用户信息更新成功，用户ID: " + user.getId());
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "用户信息更新失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 用户注册
     * @param user 要注册的用户对象
     * @return 影响的行数
     * @throws SQLException 数据库操作异常
     */
    public int registerUser(User user) throws SQLException {
        if (user == null) {
            throw new IllegalArgumentException("用户对象不能为空");
        }
        
        // 检查用户名是否已存在
        if (findByUsername(user.getName()) != null) {
            throw new SQLException("用户名已存在: " + user.getName());
        }
        
        try {
            Object[] params = {user.getId(), user.getName(), user.getPassword(), 
                             user.getTel(), user.getGender(), user.getRole()};
            int result = queryRunner.update(SQL_INSERT_USER, params);
            if (result > 0) {
                logger.info("用户注册成功: " + user.getName());
            }
            return result;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "用户注册失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 根据用户名查询用户信息
     * @param name 用户名
     * @return 用户对象，如果不存在返回null
     * @throws SQLException 数据库操作异常
     */
    public User findByUsername(String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        
        try {
            return queryRunner.query(SQL_FIND_BY_USERNAME, new BeanHandler<>(User.class), name);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "查询用户失败: " + e.getMessage(), e);
            throw e;
        }
    }
    
    /**
     * 验证用户输入的有效性
     * @param user 用户对象
     * @return 验证结果
     */
    public boolean validateUser(User user) {
        if (user == null) {
            return false;
        }
        
        // 检查必填字段
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return false;
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false;
        }
        
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            return false;
        }
        
        // 可以添加更多验证规则，如密码强度、电话号码格式等
        return true;
    }
}
