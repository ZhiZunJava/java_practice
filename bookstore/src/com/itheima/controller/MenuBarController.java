package com.itheima.controller;

import com.itheima.utils.PaneUtils;
import com.itheima.utils.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
public class MenuBarController {
    @FXML
    private Pane menuBarPane;
    /**
     *展示图书信息界面
     */
    @FXML
    public void showBooks(ActionEvent actionEvent) {
        BaseController controller = new BookController();
        PaneUtils.showAndInitPane("/com/itheima/view/bookManage.fxml",menuBarPane,controller);
    }
    /**
     * 展示借阅记录界面
     */
    @FXML
    public void showRecords(ActionEvent actionEvent) {
        BaseController controller = new RecordController();
        PaneUtils.showAndInitPane("/com/itheima/view/record.fxml",menuBarPane,controller);
    }
    /**
     * 展示修改用户信息界面
     */
    @FXML
    public void editUserInfo(ActionEvent actionEvent) {
        BaseController controller = new UserInfoController();
        PaneUtils.showAndInitPane("/com/itheima/view/editUserInfo.fxml",menuBarPane,controller);
    }
    /**
     * 展示修改密码界面
     */
    @FXML
    public void editPassword(ActionEvent actionEvent) {
        BaseController controller=new PswController();
        PaneUtils.showAndInitPane("/com/itheima/view/editPsw.fxml",menuBarPane,controller);
    }

    /**
     *退出登录
     */
    @FXML
    public void logout(ActionEvent event) {
        //清空当前登录的用户信息
        Session.setUser(null);
        BaseController controller=new LoginController();
        PaneUtils.creatNewStage("/com/itheima/view/login.fxml",menuBarPane,controller);
    }
    /**
     *退出系统
     */
    @FXML
    public void exitApp(ActionEvent event) {
        System.exit(0);
    }
}
