package com.itheima.controller;


import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class UserInfoController extends BaseController {
    @FXML
    private ToggleGroup infoGender; //用户性别组
    @FXML
    private TextField infoTel;     //用户手机号文本框
    @FXML
    private Label infoTelLab;     //用户手机号提示信息标签
    private UserDao userDao = new UserDao();
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * 修改用户信息
     */
    @FXML
    public void editUserInfo(ActionEvent event) {
        //判断手机号提示标签的内容是否为空字符串
        boolean labFlage = "".equals(infoTelLab.getText());
        //判断手机号文本框中的文本是否不为空
        boolean textFlage = !infoTel.getText().isEmpty();
        //校验通过
        if (labFlage & textFlage) {
            try {
                //获取当前登录的用户信息
                User user = Session.getUser();
                //设置用户手机号
                user.setTel(infoTel.getText());
                RadioButton rb = (RadioButton) infoGender.getSelectedToggle();
                //设置用户的性别
                user.setGender(rb.getText());
                //修改用户信息
                userDao.editUser(user);
                //设置信息框内容
                alert.setContentText("修改成功！");
                alert.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            alert.setContentText("请正确输入用户信息！");
            alert.show();
        }
    }

    /**
     * 界面内容初始化
     */
    @Override
    public void initFrame() {
        //获取当前登录的用户信息
        User user = Session.getUser();
        //将用户的手机号设置在文本框中
        infoTel.setText(user.getTel());
        //获取用户的性别
        String gnder = user.getGender();
        //遍历性别单选框组
        for (Toggle toggle : infoGender.getToggles()) {
            ToggleButton toggleButton = (ToggleButton) toggle;
            //将用户的性别设置为选中状态
            if (toggleButton.getText().equals(gnder)) {
                toggleButton.setSelected(true);
                break;
            }
        }
        //为用户手机号文本框绑定失去焦点事件
        infoTel.focusedProperty().addListener((observable, oldValue, newValue) -> {
            //当失去焦点时
            if (!newValue) {
                //获取当前手机号文本框中的文本
                String tel = infoTel.getText();
                //判断当前手机号文本框中文本内容是否符合手机号规则
                boolean flag = tel.matches("^((13[0-9])|" +
                        "(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");
                if (!flag) {
                    infoTelLab.setText("请输入正确格式的手机号！");
                    infoTelLab.setTextFill(Color.RED);
                } else {
                    infoTelLab.setText("");
                }
            }
        });
    }
}
