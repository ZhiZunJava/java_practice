package task03;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    public static List<PrintWriter> allOut = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务器启动，端口9999...");

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ServerReaderThread(socket)).start();
        }
    }
}
