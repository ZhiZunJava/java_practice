package com.itheima.enums;

/**
 * 系统常量定义
 */
public class Constants {
    
    /**
     * 用户角色常量
     */
    public static class UserRole {
        public static final String CUSTOMER = "顾客";
        public static final String ADMIN = "管理员";
        public static final String[] ALL_ROLES = {CUSTOMER, ADMIN};
    }
    
    /**
     * 图书状态常量
     */
    public static class BookState {
        public static final String AVAILABLE = "可借阅";
        public static final String BORROWED = "借阅中";
        public static final String OFFLINE = "已下架";
        public static final String[] ALL_STATES = {AVAILABLE, BORROWED, OFFLINE};
        public static final String[] CUSTOMER_STATES = {AVAILABLE, BORROWED};
    }
    
    /**
     * 错误消息常量
     */
    public static class ErrorMessage {
        // 登录相关
        public static final String EMPTY_LOGIN_FIELDS = "用户名或密码不能为空！";
        public static final String LOGIN_FAILED = "用户名或密码错误！";
        public static final String LOGIN_ERROR = "登录过程中发生错误，请稍后重试！";
        public static final String SELECT_ROLE = "请选择用户角色！";
        
        // 注册相关
        public static final String REGISTRATION_FAILED = "注册失败，请稍后重试！";
        public static final String USERNAME_EXISTS = "用户名已存在！";
        public static final String PASSWORDS_NOT_MATCH = "两次输入的密码不一致！";
        
        // 图书相关
        public static final String BOOK_NAME_REQUIRED = "书名和作者为必填项！";
        public static final String SELECT_BOOK_FIRST = "请先选中图书列表中的具体图书！";
        public static final String BORROWED_BOOK_CANNOT_EDIT = "借阅中的图书不能被修改！";
        public static final String SELECT_BOOK_TO_LEND = "请先选中对应的图书！";
        
        // 通用错误
        public static final String OPERATION_FAILED = "操作失败！";
        public static final String PAGE_NAVIGATION_FAILED = "页面跳转失败，请稍后重试！";
        public static final String DATA_VALIDATION_FAILED = "数据验证失败！";
    }
    
    /**
     * 成功消息常量
     */
    public static class SuccessMessage {
        public static final String LOGIN_SUCCESS = "登录成功！";
        public static final String REGISTRATION_SUCCESS = "注册成功！";
        public static final String BOOK_ADD_SUCCESS = "添加成功！";
        public static final String BOOK_EDIT_SUCCESS = "修改成功！";
        public static final String BOOK_LEND_SUCCESS = "借阅成功！";
        public static final String BOOK_RETURN_SUCCESS = "归还成功！";
        public static final String PASSWORD_CHANGE_SUCCESS = "密码修改成功！";
    }
    
    /**
     * 界面路径常量
     */
    public static class ViewPath {
        public static final String LOGIN = "/com/itheima/view/login.fxml";
        public static final String REGISTER = "/com/itheima/view/register.fxml";
        public static final String BOOK_MANAGE = "/com/itheima/view/bookManage.fxml";
        public static final String RECORD = "/com/itheima/view/record.fxml";
        public static final String PASSWORD_CHANGE = "/com/itheima/view/password.fxml";
    }
    
    /**
     * 数据库相关常量
     */
    public static class Database {
        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }
    
    /**
     * 验证相关常量
     */
    public static class Validation {
        public static final int MIN_PASSWORD_LENGTH = 6;
        public static final int MAX_USERNAME_LENGTH = 20;
        public static final int MAX_BOOK_NAME_LENGTH = 100;
        public static final int MAX_AUTHOR_LENGTH = 50;
    }
} 