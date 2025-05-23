package task05;

import java.io.*;
import java.util.Scanner;

public class DiaryApp {
    private static final String DIARY_DIR = "D:\\diary";

    public static void main(String[] args) {
        File dir = new File(DIARY_DIR);
        if (!dir.exists()) {
            dir.mkdirs(); // 自动创建目录
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== 日记本管理程序 ====");
            System.out.println("1. 新建日记本");
            System.out.println("2. 写日记");
            System.out.println("3. 修改日记");
            System.out.println("4. 查看日记");
            System.out.println("5. 退出程序");
            System.out.print("请输入功能编号：");

            int choice = sc.nextInt();
            sc.nextLine(); // 清除回车

            if (choice == 1) {
                createDiary(sc);
            } else if (choice == 2) {
                writeDiary(sc);
            } else if (choice == 3) {
                modifyDiary(sc);
            } else if (choice == 4) {
                readDiary(sc);
            } else if (choice == 5) {
                System.out.println("程序已退出！");
                break;
            } else {
                System.out.println("无效的功能编号！");
            }
        }
    }

    // 1. 新建
    private static void createDiary(Scanner sc) {
        System.out.print("请输入新建日记本名称：");
        String name = sc.nextLine();
        File file = new File(DIARY_DIR, name + ".txt");
        if (file.exists()) {
            System.out.println("该日记本已存在！");
        } else {
            try {
                if (file.createNewFile()) {
                    System.out.println("新建成功：" + file.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("创建失败！");
            }
        }
    }

    // 2. 写日记
    private static void writeDiary(Scanner sc) {
        System.out.print("请输入要写入的日记本名称：");
        String name = sc.nextLine();
        File file = new File(DIARY_DIR, name + ".txt");

        if (!file.exists()) {
            System.out.println("该日记本不存在，是否创建？(y/n)");
            if (sc.nextLine().equalsIgnoreCase("y")) {
                createDiaryByName(name);
            } else {
                return;
            }
        }

        // 若已有内容，则询问覆盖 or 追加
        if (file.length() > 0) {
            System.out.println("该日记本已有内容。选择操作：1. 覆盖  2. 追加  3. 取消");
            String option = sc.nextLine();
            if (option.equals("3")) return;

            boolean append = option.equals("2");
            System.out.println("请输入日记内容（结束输入请输入 exit）：");
            writeContent(sc, file, append);
        } else {
            System.out.println("请输入日记内容（结束输入请输入 exit）：");
            writeContent(sc, file, false);
        }
    }

    private static void writeContent(Scanner sc, File file, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
            while (true) {
                String line = sc.nextLine();
                if ("exit".equalsIgnoreCase(line)) break;
                writer.write(line);
                writer.newLine();
            }
            System.out.println("写入完成！");
        } catch (IOException e) {
            System.out.println("写入失败！");
        }
    }

    // 3. 修改日记（重新覆盖）
    private static void modifyDiary(Scanner sc) {
        System.out.print("请输入要修改的日记本名称：");
        String name = sc.nextLine();
        File file = new File(DIARY_DIR, name + ".txt");

        if (!file.exists()) {
            System.out.println("该日记本不存在！");
            return;
        }

        if (file.length() == 0) {
            System.out.println("日记本为空，无法修改！");
            return;
        }

        System.out.println("请输入新的内容（结束输入请输入 exit）：");
        writeContent(sc, file, false);
    }

    // 4. 查看日记
    private static void readDiary(Scanner sc) {
        System.out.print("请输入要查看的日记本名称：");
        String name = sc.nextLine();
        File file = new File(DIARY_DIR, name + ".txt");

        if (!file.exists()) {
            System.out.println("该日记本不存在！");
            return;
        }

        System.out.println("====== 日记内容如下 ======");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("读取失败！");
        }
    }

    // 创建指定名称的日记本
    private static void createDiaryByName(String name) {
        File file = new File(DIARY_DIR, name + ".txt");
        try {
            if (file.createNewFile()) {
                System.out.println("已创建新日记本！");
            }
        } catch (IOException e) {
            System.out.println("创建失败！");
        }
    }
}
