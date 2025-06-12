package com.itheima.utils;

import javafx.scene.control.Alert;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 消息显示工具类
 * 提供统一的消息显示方法
 */
public class MessageUtils {
    private static final Logger logger = Logger.getLogger(MessageUtils.class.getName());
    
    /**
     * 显示信息消息
     * @param message 消息内容
     */
    public static void showInfo(String message) {
        showAlert(Alert.AlertType.INFORMATION, "信息", message);
        logger.info("显示信息消息: " + message);
    }
    
    /**
     * 显示警告消息
     * @param message 消息内容
     */
    public static void showWarning(String message) {
        showAlert(Alert.AlertType.WARNING, "警告", message);
        logger.warning("显示警告消息: " + message);
    }
    
    /**
     * 显示错误消息
     * @param message 消息内容
     */
    public static void showError(String message) {
        showAlert(Alert.AlertType.ERROR, "错误", message);
        logger.log(Level.SEVERE, "显示错误消息: " + message);
    }
    
    /**
     * 显示成功消息
     * @param message 消息内容
     */
    public static void showSuccess(String message) {
        showAlert(Alert.AlertType.INFORMATION, "成功", message);
        logger.info("显示成功消息: " + message);
    }
    
    /**
     * 显示确认对话框
     * @param message 消息内容
     * @return 用户是否点击了确认按钮
     */
    public static boolean showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认");
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        logger.info("显示确认对话框: " + message);
        return alert.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK;
    }
    
    /**
     * 记录并显示异常信息
     * @param exception 异常对象
     * @param userMessage 给用户显示的消息
     */
    public static void logAndShowError(Exception exception, String userMessage) {
        logger.log(Level.SEVERE, "发生异常: " + exception.getMessage(), exception);
        showError(userMessage);
    }
    
    /**
     * 显示Alert对话框的通用方法
     */
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
} 