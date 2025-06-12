package com.itheima;


import com.itheima.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
public class BookStoreApp extends Application  {
    @Override
    public void start(Stage stage) throws IOException {
        //加载用户登录对应的FXML文件
        FXMLLoader fxmlLoader = new FXMLLoader(BookStoreApp.class
                        .getResource("/com/itheima/view/login.fxml"));
        //创建场景
        Scene scene = new Scene(fxmlLoader.load(), 605, 410);
        //获取用户登录控制器类
        LoginController controller = fxmlLoader.getController();
   /*     RegisterController controller = fxmlLoader.getController();*/
        controller.initFrame();
        //设置窗口标题
        stage.setTitle("黑马书屋");
        stage.setScene(scene);
        stage.show();
    }
}
