package com.itheima.utils;


import com.itheima.controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class PaneUtils {
    public static void creatNewStage(String url, Pane pane, BaseController controller){
        try{
            //加载添加图书对应的FXML文件
            FXMLLoader fxmlLoader = new FXMLLoader(
                    PaneUtils.class.getResource(url));
            //创建场景
            Scene scene = new Scene(fxmlLoader.load(), 605, 410);
            //控制器类
           controller = fxmlLoader.getController();
           controller.initFrame();
            Stage stage = new Stage();
            //设置窗口标题
            stage.setTitle("黑马书屋");
            stage.setScene(scene);
            stage.show();
            Stage s =(Stage) pane.getScene().getWindow();
            s.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
 public static void  showAndInitPane(String url, Pane pane, BaseController controller){
      try {
          //加载添加图书对应的FXML文件
          FXMLLoader fxmlLoader = new FXMLLoader(
                  PaneUtils.class.getResource(url));
          //创建场景
          Scene scene = new Scene(fxmlLoader.load(), 605, 410);
          Stage stage = (Stage) pane.getScene().getWindow();
          //获取控制器类
          controller = fxmlLoader.getController();
          controller.initFrame();
          stage.setScene(scene);
          // 关闭之前的所有布局
          pane.getChildren().clear();
          stage.show();
      } catch (IOException e) {
          throw new RuntimeException(e);
      }
    }
}
