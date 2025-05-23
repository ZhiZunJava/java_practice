package task02;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class ServerReaderRunnable implements Runnable {
    private final Socket socket;

    public ServerReaderRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            PrintStream ps = new PrintStream(outputStream, true, "UTF-8");

            // HTTP响应头
            ps.println("HTTP/1.1 200 OK");
            ps.println("Content-Type: text/html; charset=UTF-8");
            ps.println(); // 必须空一行

            // HTTP响应体
            ps.println("<div style='color:red;font-size:20px;text-align:center;'>");
            ps.println("欢迎所有致力于和平利用太空的国家及地区与我国开展合作，一起参与中国空间站飞行任务。");
            ps.println("</div>");

            ps.close();
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
