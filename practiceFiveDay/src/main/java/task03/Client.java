package task03;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9999);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

        // 创建线程监听服务器广播消息
        new Thread(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        System.out.print("请输入用户名：");
        String name = keyboard.readLine();
        pw.println(name); // 首次输入为用户名

        // 聊天输入循环
        String msg;
        while ((msg = keyboard.readLine()) != null) {
            pw.println(msg);
        }
    }
}
