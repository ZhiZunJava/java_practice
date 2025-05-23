package task03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerReaderThread implements Runnable {
    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        ) {
            Server.allOut.add(pw);
            String name = br.readLine();
            sendMsg("【系统】：欢迎用户 " + name + " 加入时代先锋研习社！");

            String line;
            while ((line = br.readLine()) != null) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                sendMsg("【" + name + "】：" + line + "（" + time + "）");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String msg) {
        for (PrintWriter out : Server.allOut) {
            out.println(msg);
        }
    }
}
