package task02;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MilepostService service = new MilepostService();

        while (true) {
            System.out.println("\n====== 里程碑管理 ======");
            System.out.println("1. 新增里程碑");
            System.out.println("2. 查询里程碑");
            System.out.println("3. 编辑里程碑");
            System.out.println("4. 删除里程碑");
            System.out.println("0. 退出程序");
            System.out.print("请输入您要做的操作：");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    service.addMilepost(sc);
                    break;
                case "2":
                    service.queryMileposts(sc);
                    break;
                case "3":
                    service.editMilepost(sc);
                    break;
                case "4":
                    service.deleteMilepost(sc);
                    break;
                case "0":
                    System.out.println("程序退出！");
                    return;
                default:
                    System.out.println("输入无效！");
            }
        }
    }
}
