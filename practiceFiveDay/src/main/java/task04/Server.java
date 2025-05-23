package task04;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket controlServer = new ServerSocket(8888);
        System.out.println("控制通道已启动，端口8888");

        while (true) {
            Socket controlSocket = controlServer.accept();
            new Thread(new ServerThread(controlSocket)).start();
        }
    }
}
