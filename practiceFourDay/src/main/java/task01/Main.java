package task01;

import java.util.*;

public class Main {
    static final String userPath = "src/main/java/task01/users.txt";
    static final String candidatePath = "src/main/java/task01/campaigner.txt";
    static final String resultPath = "src/main/java/task01/votingResults.txt";
    static User currentUser = null;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        List<User> users = DataManager.loadUsers(userPath);
        List<Candidate> candidates = DataManager.loadCandidates(candidatePath);

        while (true) {
            System.out.println("\n---欢迎进入投票系统---");
            System.out.println("1. 用户注册");
            System.out.println("2. 用户登录");
            System.out.println("3. 查看竞选人");
            System.out.println("4. 投票");
            System.out.println("5. 结束投票");
            System.out.println("0. 退出程序");
            System.out.print("请选择操作：");

            String choice = sc.nextLine();
            if (choice.equals("1")) {
                System.out.print("请输入 学号 姓名 密码（空格分隔）：");
                String[] input = sc.nextLine().split(" ");
                users.add(new User(input[0], input[1], input[2], false));
                DataManager.saveUsers(userPath, users);
                System.out.println("注册成功，请登录！");
            } else if (choice.equals("2")) {
                System.out.print("请输入 学号 密码：");
                String[] input = sc.nextLine().split(" ");
                for (User u : users) {
                    if (u.id.equals(input[0]) && u.password.equals(input[1])) {
                        currentUser = u;
                        System.out.println("登录成功，欢迎 " + u.name + "！");
                    }
                }
                if (currentUser == null) System.out.println("账号或密码错误！");
            } else if (choice.equals("3")) {
                System.out.println("候选人信息如下：");
                for (Candidate c : candidates) {
                    System.out.println("姓名：" + c.name + "，学分：" + c.score + "，宣言：" + c.slogan);
                }
            } else if (choice.equals("4")) {
                if (currentUser == null) {
                    System.out.println("请先登录！");
                    continue;
                }
                if (currentUser.voted) {
                    System.out.println("您已投票，不能重复投票！");
                    continue;
                }

                System.out.println("请输入第一个候选人姓名：");
                String name1 = sc.nextLine();
                System.out.println("请输入第二个候选人姓名：");
                String name2 = sc.nextLine();

                boolean found1 = false, found2 = false;
                for (Candidate c : candidates) {
                    if (c.name.equals(name1)) {
                        c.voteCount++;
                        found1 = true;
                    }
                    if (c.name.equals(name2)) {
                        c.voteCount++;
                        found2 = true;
                    }
                }

                if (found1 && found2) {
                    currentUser.voted = true;
                    DataManager.saveUsers(userPath, users);
                    DataManager.saveCandidates(candidatePath, candidates);
                    System.out.println("投票成功！");
                } else {
                    System.out.println("候选人姓名有误！");
                }
            } else if (choice.equals("5")) {
                DataManager.writeResult(candidates, resultPath);
                System.out.println("投票结果已保存至：" + resultPath);
            } else if (choice.equals("0")) {
                break;
            }
        }
    }
}
