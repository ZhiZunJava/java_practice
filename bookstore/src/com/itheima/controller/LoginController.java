package com.itheima.controller;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.PaneUtils;
import com.itheima.utils.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 用户登录控制器
 * 负责用户登录界面的逻辑处理
 */
public class LoginController extends BaseController {
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    
    // 常量定义
    private static final String ROLE_CUSTOMER = "顾客";
    private static final String ROLE_ADMIN = "管理员";
    private static final String[] USER_ROLES = {ROLE_CUSTOMER, ROLE_ADMIN};
    
    // 错误消息常量
    private static final String MSG_EMPTY_FIELDS = "用户名或密码不能为空！";
    private static final String MSG_LOGIN_FAILED = "用户名或密码错误！";
    private static final String MSG_LOGIN_ERROR = "登录过程中发生错误，请稍后重试！";
    
    // FXML组件
    @FXML
    private Pane loginPane;          // 用户登录面板
    @FXML
    private TextField loginName;     // 用户名文本框
    @FXML
    private PasswordField loginPsw;  // 密码框
    @FXML
    private ComboBox<String> loginRole; // 角色下拉框
    
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private final UserDao userDao = new UserDao();
    
    /**
     * 初始化界面
     */
    @Override
    public void initFrame() {
        setupRoleComboBox();
        clearInputFields();
        logger.info("登录界面初始化完成");
    }
    
    /**
     * 设置角色下拉框
     */
    private void setupRoleComboBox() {
        ObservableList<String> observableList = FXCollections.observableArrayList(USER_ROLES);
        loginRole.setItems(observableList);
        loginRole.getSelectionModel().select(0); // 默认选择第一个角色
    }
    
    /**
     * 清空输入字段
     */
    private void clearInputFields() {
        loginName.clear();
        loginPsw.clear();
    }
    
    /**
     * 跳转到注册页面
     */
    @FXML
    public void toRegisterPage(ActionEvent actionEvent) {
        try {
            BaseController registerController = new RegisterController();
            PaneUtils.showAndInitPane("/com/itheima/view/register.fxml", loginPane, registerController);
            logger.info("跳转到注册页面");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "跳转注册页面失败: " + e.getMessage(), e);
            showErrorMessage("页面跳转失败，请稍后重试！");
        }
    }
    
    /**
     * 用户登录处理
     */
    @FXML
    public void toLogin(ActionEvent actionEvent) {
        try {
            // 获取并验证输入
            LoginRequest loginRequest = getAndValidateInput();
            if (loginRequest == null) {
                return; // 验证失败，已显示错误消息
            }
            
            // 执行登录
            User loggedInUser = performLogin(loginRequest);
            if (loggedInUser == null) {
                showErrorMessage(MSG_LOGIN_FAILED);
                return;
            }
            
            // 登录成功，跳转到主界面
            handleLoginSuccess(loggedInUser);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "登录失败: " + e.getMessage(), e);
            showErrorMessage(MSG_LOGIN_ERROR);
        }
    }
    
    /**
     * 获取并验证用户输入
     */
    private LoginRequest getAndValidateInput() {
        String username = loginName.getText();
        String password = loginPsw.getText();
        String role = loginRole.getSelectionModel().getSelectedItem();
        
        // 验证输入是否为空
        if (isNullOrEmpty(username) || isNullOrEmpty(password)) {
            showErrorMessage(MSG_EMPTY_FIELDS);
            return null;
        }
        
        // 验证角色是否有效
        if (role == null) {
            showErrorMessage("请选择用户角色！");
            return null;
        }
        
        return new LoginRequest(username.trim(), password, role);
    }
    
    /**
     * 执行登录操作
     */
    private User performLogin(LoginRequest loginRequest) {
        try {
            User user = new User(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getRole());
            
            // 验证用户数据
            if (!userDao.validateUser(user)) {
                logger.warning("用户数据验证失败: " + loginRequest.getUsername());
                return null;
            }
            
            // 执行登录
            User loggedInUser = userDao.login(user);
            if (loggedInUser != null) {
                logger.info("用户登录成功: " + loginRequest.getUsername() + ", 角色: " + loginRequest.getRole());
            } else {
                logger.warning("用户登录失败: " + loginRequest.getUsername());
            }
            
            return loggedInUser;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "登录过程中发生异常: " + e.getMessage(), e);
            throw new RuntimeException("登录失败", e);
        }
    }
    
    /**
     * 处理登录成功
     */
    private void handleLoginSuccess(User loggedInUser) {
        try {
            // 将用户信息存入Session
            Session.setUser(loggedInUser);
            
            // 跳转到图书管理界面
            BaseController bookController = new BookController();
            PaneUtils.creatNewStage("/com/itheima/view/bookManage.fxml", loginPane, bookController);
            
            logger.info("用户 " + loggedInUser.getName() + " 登录成功并跳转到主界面");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "跳转主界面失败: " + e.getMessage(), e);
            showErrorMessage("跳转主界面失败，请稍后重试！");
        }
    }
    
    /**
     * 显示错误消息
     */
    private void showErrorMessage(String message) {
        alert.setAlertType(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    
    /**
     * 显示信息消息
     */
    private void showInfoMessage(String message) {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
    
    /**
     * 检查字符串是否为空或null
     */
    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 登录请求内部类
     */
    private static class LoginRequest {
        private final String username;
        private final String password;
        private final String role;
        
        public LoginRequest(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
        
        public String getUsername() {
            return username;
        }
        
        public String getPassword() {
            return password;
        }
        
        public String getRole() {
            return role;
        }
    }
}
