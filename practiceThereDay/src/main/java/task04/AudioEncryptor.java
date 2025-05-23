package task04;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class AudioEncryptor {
    private static final String SOURCE_DIR = "D:\\audio\\source";
    private static final String ENCRYPT_DIR = "D:\\audio\\encryption";
    private static final String DECRYPT_DIR = "D:\\audio\\decrypt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n音频加密解密程序");
            System.out.println("1. 音频加密");
            System.out.println("2. 音频解密");
            System.out.println("3. 退出程序");
            System.out.print("请输入功能编号：");
            int choice = sc.nextInt();
            sc.nextLine(); // 清空换行符

            switch (choice) {
                case 1:
                    encryptAudio(sc);
                    break;
                case 2:
                    decryptAudio(sc);
                    break;
                case 3:
                    System.out.println("已退出程序！");
                    return;
                default:
                    System.out.println("输入无效，请重新选择！");
                    break;
            }
        }
    }

    private static void encryptAudio(Scanner sc) {
        listFiles(SOURCE_DIR);
        System.out.print("请输入要加密的音频文件名（含后缀）：");
        String filename = sc.nextLine();
        File sourceFile = new File(SOURCE_DIR, filename);
        if (!sourceFile.exists()) {
            System.out.println("文件不存在！");
            return;
        }

        File encryptedFile = new File(ENCRYPT_DIR, filename.replace(".mp3", "（加密）.mp3"));
        try {
            processFile(sourceFile, encryptedFile);
            System.out.println("加密成功！加密文件路径：" + encryptedFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("加密失败！");
        }
    }

    private static void decryptAudio(Scanner sc) {
        listFiles(ENCRYPT_DIR);
        System.out.print("请输入要解密的音频文件名（含后缀）：");
        String filename = sc.nextLine();
        File encryptedFile = new File(ENCRYPT_DIR, filename);
        if (!encryptedFile.exists()) {
            System.out.println("文件不存在！");
            return;
        }

        File decryptedFile = new File(DECRYPT_DIR, filename.replace("（加密）", "（解密）"));
        try {
            processFile(encryptedFile, decryptedFile);
            System.out.println("解密成功！解密文件路径：" + decryptedFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("解密失败！");
        }
    }

    // 异或加密/解密通用方法
    private static void processFile(File inputFile, File outputFile) throws IOException {
        try (
                InputStream in = new FileInputStream(inputFile);
                OutputStream out = new FileOutputStream(outputFile)
        ) {
            int data;
            while ((data = in.read()) != -1) {
                out.write(data ^ 5); // 使用固定数字异或加密/解密
            }
        }
    }

    // 显示指定目录下的所有文件
    private static void listFiles(String dirPath) {
        System.out.println("当前目录：" + dirPath);
        try {
            Files.list(Paths.get(dirPath))
                    .filter(Files::isRegularFile)
                    .forEach(path -> System.out.println(path.getFileName()));
        } catch (IOException e) {
            System.out.println("读取文件列表失败！");
        }
    }
}
