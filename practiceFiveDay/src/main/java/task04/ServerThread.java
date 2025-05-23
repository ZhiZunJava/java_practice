package task04;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerThread implements Runnable {
    private final Socket controlSocket;
    private final String BASE_DIR = "server_files";

    public ServerThread(Socket socket) {
        this.controlSocket = socket;
        new File(BASE_DIR).mkdirs();
    }

    @Override
    public void run() {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
                PrintWriter pw = new PrintWriter(controlSocket.getOutputStream(), true)
        ) {
            pw.println("欢迎使用网盘系统！");
            while (true) {
                pw.println("==== 菜单 ====");
                pw.println("1. 查看文件列表");
                pw.println("2. 上传文件");
                pw.println("3. 下载文件");
                pw.println("4. 退出");
                pw.println("end");

                String cmd = br.readLine();
                if (cmd == null) break;

                switch (cmd.trim()) {
                    case "1":
                        listFiles(pw);
                        break;
                    case "2":
                        String uploadName = br.readLine();
                        long uploadSize = Long.parseLong(br.readLine());

                        new Thread(() -> {
                            try (ServerSocket dataServer = new ServerSocket(9999);
                                 Socket dataSocket = dataServer.accept()) {
                                new DataTransferThread(dataSocket, new File(BASE_DIR, uploadName), uploadSize, true).run();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        break;

                    case "3":
                        String downloadName = br.readLine();
                        if (downloadName == null) {
                            break;
                        }
                        File file = new File(BASE_DIR, downloadName);
                        if (!file.exists() || !file.isFile()) {
                            pw.println("文件不存在！");
                            break;
                        }

                        pw.println("文件大小：" + file.length());

                        new Thread(() -> {
                            try (ServerSocket dataServer = new ServerSocket(9999);
                                 Socket dataSocket = dataServer.accept()) {
                                new DataTransferThread(dataSocket, file, file.length(), false).run();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                        break;

                    case "4":
                        pw.println("退出连接！");
                        return;

                    default:
                        pw.println("无效指令！");
                }
            }
        } catch (Exception e) {
            System.out.println("控制通道异常关闭：" + e.getMessage());
        }
    }

    private void listFiles(PrintWriter pw) {
        File[] files = new File(BASE_DIR).listFiles();
        if (files == null || files.length == 0) {
            pw.println("无文件！");
        } else {
            for (File f : files) {
                if (f.isFile()) {
                    pw.println("- " + f.getName());
                }
            }
        }
    }
}