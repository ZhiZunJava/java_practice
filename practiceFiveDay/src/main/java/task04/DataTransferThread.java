package task04;

import java.io.*;
import java.net.Socket;

public class DataTransferThread implements Runnable {
    private final Socket dataSocket;
    private final File file;
    private final long size;
    private final boolean isUpload; // true: upload, false: download

    public DataTransferThread(Socket socket, File file, long size, boolean isUpload) {
        this.dataSocket = socket;
        this.file = file;
        this.size = size;
        this.isUpload = isUpload;
    }

    @Override
    public void run() {
        try {
            if (isUpload) {
                try (FileOutputStream fos = new FileOutputStream(file);
                     InputStream in = dataSocket.getInputStream()) {
                    byte[] buf = new byte[1024];
                    long received = 0;
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        received += len;
                    }
                    if (received != size) {
                        System.out.println("警告：上传的文件大小不匹配，期望: " + size + "，实际: " + received);
                    }
                }
            } else {
                try (FileInputStream fis = new FileInputStream(file);
                     OutputStream out = dataSocket.getOutputStream()) {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = fis.read(buf)) != -1) {
                        out.write(buf, 0, len);
                    }
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("数据通道异常：" + e.getMessage());
        } finally {
            try {
                dataSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}