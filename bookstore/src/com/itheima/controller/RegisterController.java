package com.itheima.controller;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.PaneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.sql.SQLException;

public class RegisterController extends BaseController {
    @FXML
    private Pane registerPane;    //用户注册面板
    @FXML
    private TextField regName;    //用户名文本框
    @FXML
    private PasswordField regPsw; //密码框
    @FXML
    private PasswordField confPsw;//确认密码的密码框
    @FXML
    private ToggleGroup gender;   //性别选项组
    @FXML
    private TextField regTel;     //手机号文本框
    @FXML
    private Label regNameLab;     //用户名提示标签
    @FXML
    private Label regPswLab;      //密码提示标签
    @FXML
    private Label regCofPswLab;   //确认密码提示标签
    @FXML
    private Label regTelLab;      //手机号提示标签
    private UserDao userDao=new UserDao();
    @FXML
    public void toLoginPage(ActionEvent event) {
        BaseController loginController = new LoginController();
        //加载用户登录界面，并初始化界面内容
        PaneUtils.showAndInitPane("/com/itheima/view/login.fxml", registerPane, loginController);
    }
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    public void toRegister(ActionEvent event) {
        //获取用户输入的信息
        User user = getUserInfo();
        //判断提示标签中是否不是*的内容
        boolean labFlage="*".equals(regNameLab.getText())&&
                "*".equals(regPswLab.getText())&&
                "*".equals(regCofPswLab.getText())&&
                "*".equals(regTelLab.getText());
        //判断输入框中的文本是否为空
        boolean textFlage=!user.getName().isEmpty()&&
                !user.getPassword().isEmpty()&&
                !confPsw.getText().isEmpty()&&
                !user.getTel().isEmpty();
        //如果提示框中没有校验失败的提示，并且输入框文本都不为空
        if(labFlage&&textFlage){
            try {
                //注册用户
                userDao.registerUser(user);
                alert.setContentText("注册成功！");
                alert.show();
                //注册成功后，清空文本框中的内容
                regName.setText("");
                regPsw.setText("");
                confPsw.setText("");
                regTel.setText("");
                gender.selectToggle(gender.getToggles().get(0));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            alert.setContentText("请正确输入注册信息！");
            alert.show();
        }
    }

    /**
     * 获取用户输入的信息
     */
    private User getUserInfo() {
        //获取用户名文本框中的文本
        String name = regName.getText();
        //获取密码框中的文本
        String psw = regPsw.getText();
        //获取当前选择的性别
        String gd = ((RadioButton) gender.getSelectedToggle()).getText();
        //获取手机号文本框的文本
        String tel = regTel.getText();
        //默认设置用户的角色为顾客
        return new User(null, name, psw, tel, gd, "顾客");
    }
    @Override
    public void initFrame( ) {
        //监听用户名文本框，添加失去焦点事件
        regName.focusedProperty().addListener((observable, oldVal, newVal) -> {
            //如果失去焦点
            if (!newVal) {
                try {
                    //获取用户名文本框中的文本
                    String name = regName.getText();
                    if (name.isEmpty()) {
                        regNameLab.setText("用户不能为空！");
                    } else {
                        //根据文本框输入的用户名查询数据库中对应的用户信息
                        User u = userDao.findByUsername(name);
                        if (u != null) {
                            regNameLab.setText("用户已存在，请重新输入！");
                        } else {
                            regNameLab.setText("*");
                        }
                    }
                    regNameLab.setTextFill(Color.RED);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //监听密码框，添加失去焦点事件
        regPsw.focusedProperty().addListener((observable, oldVal, newVal) -> {
            //如果失去焦点
            if (!newVal) {
                //获取密码框中的文本
                String psw = regPsw.getText();
                if (psw.isEmpty()) {
                    regPswLab.setText("密码不能为空！");
                } else {
                    regPswLab.setText("*");
                }
                regPswLab.setTextFill(Color.RED);
            }
        });
        //监听确认密码的密码框，添加失去焦点事件
        confPsw.focusedProperty().addListener((observable, oldVal, newVal) -> {
            //如果失去焦点
            if (!newVal) {
                //获取确认密码框中的文本
                String cpsw = confPsw.getText();
                //获取密码框中的文本
                String psw = regPsw.getText();
                //如果确认密码和密码不一致，或者为空
                if (cpsw.isEmpty()||!cpsw.equals(psw)) {
                    regCofPswLab.setText("确认密码不能为空，且需要和密码一致！");
                }else {
                    regCofPswLab.setText("*");
                }
                regCofPswLab.setTextFill(Color.RED);
            }
        });
        //监听手机号文本框，添加失去焦点事件
        regTel.focusedProperty().addListener((observable, oldVal, newVal) -> {
            //失去焦点
            if (!newVal) {
                //获取手机号文本框的文本
                String tel = regTel.getText();
                //判断手机号的格式是否正确
                boolean flag = tel.matches("^((13[0-9])|(14[5|7])|" +
                                "(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");
                if (!flag) {
                    regTelLab.setText("请输入正确格式的手机号！");
                }else {
                    regTelLab.setText("*");
                }
                regTelLab.setTextFill(Color.RED);
            }
        });
    }
}