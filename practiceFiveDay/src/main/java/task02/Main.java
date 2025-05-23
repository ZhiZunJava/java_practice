package task02;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("------服务器启动------");

        ServerSocket serverSocket = new ServerSocket(8080);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                16, 32,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        while (true) {
            Socket socket = serverSocket.accept(); // 接收客户端请求
            ServerReaderRunnable runnable = new ServerReaderRunnable(socket);
            poolExecutor.execute(runnable);
        }
    }
}
