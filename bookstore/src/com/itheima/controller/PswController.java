package com.itheima.controller;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class PswController extends BaseController {
    @FXML
    private PasswordField oldPsw; //原始密码的密码框
    @FXML
    private PasswordField newPsw; //新密码的密码框
    @FXML
    private PasswordField confPsw;//确认密码的密码框
    @FXML
    private Label oldPswLab;      //原始密码对应的提示标签
    @FXML
    private Label newPswLab;      //新密码对应的提示标签
    @FXML
    private Label confPswLab;     //确认密码对应的提示标签
    private UserDao userDao = new UserDao();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    /**
     *修改密码
     */
    @FXML
    public void editPsw(ActionEvent event) {
        //判断提示标签的内容是否都是星号
        boolean labFlage="*".equals(oldPswLab.getText())&&
                "*".equals(newPswLab.getText())&&
                "*".equals(confPswLab.getText());
        //判断是否所有密码框中的文本都不为空
        boolean textFlage=!oldPsw.getText().isEmpty()&&
                !newPsw.getText().isEmpty()&&
                !confPsw.getText().isEmpty();
        //通过校验
        if(labFlage&textFlage){
            try {
                //获取当前登录用户的信息
                User user = Session.getUser();
                //设置用户的新密码
                user.setPassword(newPsw.getText());
                //修改用户信息
                userDao.editUser(user);
                alert.setContentText("密码修改成功！");
                alert.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            alert.setContentText("请正确输入密码信息！");
            alert.show();
        }
    }

    /**
     * 界面初始化
     */
    @Override
    public void initFrame() {
        //为原始密码的密码框绑定失去焦点事件
        oldPsw.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                //获取原始密码的密码框中的文本
                String psw = oldPsw.getText();
                //获取当前登录用户的密码
                User user = Session.getUser();
                //判断输入密码是否为空，以及是否和用户密码一致
                if (psw.isEmpty()||!psw.equals(user.getPassword())) {
                    oldPswLab.setText("原始密码错误！");
                } else {
                    oldPswLab.setText("*");
                }
                oldPswLab.setTextFill(Color.RED);
            }
        });
        //为新密码的密码框绑定失去焦点事件
        newPsw.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                //获取新密码的密码框中的文本
                String psw = newPsw.getText();
                if (psw.isEmpty()) {
                    newPswLab.setText("密码不能为空！");
                } else {
                    newPswLab.setText("*");
                }
                newPswLab.setTextFill(Color.RED);
            }
        });
        //为确认密码的密码框绑定失去焦点事件
        confPsw.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                //获取确认密码的密码框中的文本
                String cpsw = confPsw.getText();
                //获取新密码的密码框中的文本
                String psw = newPsw.getText();
                //判断确认密码是否为空，以及和新密码是否一致
                if (cpsw.isEmpty()||!cpsw.equals(psw)) {
                    confPswLab.setText("确认密码不能为空，且需要和密码一致！");
                }else {
                    confPswLab.setText("*");
                }
                confPswLab.setTextFill(Color.RED);
            }
        });
    }
}
