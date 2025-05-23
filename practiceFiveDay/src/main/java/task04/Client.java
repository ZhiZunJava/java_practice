package task04;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入服务器地址（如127.0.0.1）：");
        String host = sc.nextLine();

        try (Socket controlSocket = new Socket(host, 8888);
             BufferedReader br = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
             PrintWriter pw = new PrintWriter(controlSocket.getOutputStream(), true)) {

            String line;
            // 读取欢迎信息
            while ((line = br.readLine()) != null && !line.equals("end")) {
                System.out.println(line);
            }

            while (true) {
                System.out.print("请输入操作编号（1=列表 2=上传 3=下载 4=退出）：");
                String input = sc.nextLine();
                pw.println(input);
                pw.flush();

                switch (input.trim()) {
                    case "1":
                        while ((line = br.readLine()) != null && !line.equals("end")) {
                            System.out.println(line);
                        }
                        break;

                    case "2":
                        System.out.print("请输入本地文件路径：");
                        String path = sc.nextLine().replace("\"", "");
                        File file = new File(path);
                        if (!file.exists() || !file.isFile()) {
                            System.out.println("文件不存在或不是文件！");
                            while ((line = br.readLine()) != null && !line.equals("end")) {
                                System.out.println(line);
                            }
                            break;
                        }

                        String fileName = file.getName();
                        long fileSize = file.length();
                        pw.println(fileName);
                        pw.println(fileSize);
                        pw.flush();

                        int uploadPort = 9999;

                        try (Socket dataSocket = new Socket(host, uploadPort);
                             FileInputStream fis = new FileInputStream(file);
                             OutputStream os = dataSocket.getOutputStream()) {

                            byte[] buf = new byte[1024];
                            long sent = 0;
                            int len;
                            while ((len = fis.read(buf)) != -1) {
                                os.write(buf, 0, len);
                                sent += len;
                                printProgress(sent, fileSize, "上传中");
                            }
                            os.flush();
                            System.out.println("\n上传完成！");
                        }
                        // 读取菜单
                        while ((line = br.readLine()) != null && !line.equals("end")) {
                            System.out.println(line);
                        }
                        break;

                    case "3":
                        System.out.print("请输入要下载的文件名：");
                        String downloadName = sc.nextLine();
                        pw.println(downloadName);
                        pw.flush();

                        String sizeLine = br.readLine();
                        if (sizeLine == null) {
                            System.out.println("服务器连接断开！");
                            return;
                        }

                        if (!sizeLine.startsWith("文件大小：")) {
                            System.out.println(sizeLine);
                            // 读取菜单
                            while ((line = br.readLine()) != null && !line.equals("end")) {
                                System.out.println(line);
                            }
                            break;
                        }

                        long total = Long.parseLong(sizeLine.replace("文件大小：", "").trim());

                        int downloadPort = 9999;

                        try (Socket dataSocket = new Socket(host, downloadPort);
                             FileOutputStream fos = new FileOutputStream("download_" + downloadName);
                             BufferedInputStream bis = new BufferedInputStream(dataSocket.getInputStream())) {

                            byte[] buf = new byte[1024];
                            long received = 0;
                            int len;
                            while ((len = bis.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                                received += len;
                                printProgress(received, total, "下载中");
                            }
                            System.out.println("\n下载完成，保存为：download_" + downloadName);
                        }
                        // 读取菜单
                        while ((line = br.readLine()) != null && !line.equals("end")) {
                            System.out.println(line);
                        }
                        break;

                    case "4":
                        System.out.println("客户端退出。");
                        return;

                    default:
                        System.out.println("无效输入！");
                        // 读取菜单
                        while ((line = br.readLine()) != null && !line.equals("end")) {
                            System.out.println(line);
                        }
                }

                System.out.println("=========");
            }

        } catch (IOException e) {
            System.out.println("连接失败：" + e.getMessage());
        }
    }

    private static void printProgress(long current, long total, String prefix) {
        int percent = (int) ((current * 100) / total);
        System.out.print("\r" + prefix + "：" + percent + "% [" + current + "/" + total + "字节]");
        System.out.flush();
    }
}