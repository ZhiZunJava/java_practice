package task03;

import java.io.File;
import java.util.Scanner;

public class FileManagerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n文件搜索与删除工具");
            System.out.println("1. 指定关键词搜索文件");
            System.out.println("2. 指定后缀名搜索文件");
            System.out.println("3. 删除文件/目录");
            System.out.println("4. 退出");
            System.out.print("请输入指令：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除换行符

            switch (choice) {
                case 1:
                    searchByKeyword(scanner);
                    break;
                case 2:
                    searchBySuffix(scanner);
                    break;
                case 3:
                    deleteFileOrDir(scanner);
                    break;
                case 4:
                    System.out.println("已退出程序");
                    return;
                default:
                    System.out.println("指令无效，请重新输入！");
                    break;
            }
        }
    }

    // 1. 关键词搜索
    public static void searchByKeyword(Scanner scanner) {
        System.out.print("请输入搜索的目录路径：");
        String dirPath = scanner.nextLine();
        System.out.print("请输入关键词（多个用逗号分隔）：");
        String[] keywords = scanner.nextLine().split(",");

        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("目录无效！");
            return;
        }

        System.out.println("匹配的文件：");
        searchFilesByKeyword(dir, keywords);
    }

    private static void searchFilesByKeyword(File dir, String[] keywords) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchFilesByKeyword(file, keywords);
            } else {
                for (String key : keywords) {
                    if (file.getName().contains(key.trim())) {
                        System.out.println(file.getAbsolutePath());
                        break;
                    }
                }
            }
        }
    }

    // 2. 后缀名搜索
    public static void searchBySuffix(Scanner scanner) {
        System.out.print("请输入搜索的目录路径：");
        String dirPath = scanner.nextLine();
        System.out.print("请输入后缀名（多个用逗号分隔）：");
        String[] suffixes = scanner.nextLine().split(",");

        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("目录无效！");
            return;
        }

        System.out.println("匹配的文件：");
        searchFilesBySuffix(dir, suffixes);
    }

    private static void searchFilesBySuffix(File dir, String[] suffixes) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                searchFilesBySuffix(file, suffixes);
            } else {
                for (String suffix : suffixes) {
                    if (file.getName().endsWith(suffix.trim())) {
                        System.out.println(file.getAbsolutePath());
                        break;
                    }
                }
            }
        }
    }

    // 3. 删除文件/目录
    public static void deleteFileOrDir(Scanner scanner) {
        System.out.print("请输入要删除的文件/目录路径：");
        String path = scanner.nextLine();
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("文件/目录不存在！");
            return;
        }

        if (deleteRecursive(file)) {
            System.out.println("删除成功！");
        } else {
            System.out.println("删除失败！");
        }
    }

    private static boolean deleteRecursive(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursive(f);
                }
            }
        }
        return file.delete();
    }
}
