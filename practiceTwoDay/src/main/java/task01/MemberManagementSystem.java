package task01;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class MemberManagementSystem {
    private Map<String, Member> memberMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // 系统首页
    public void showMenu() {
        while (true) {
            System.out.println("=== 书法社团成员管理系统 ===");
            System.out.println("1. 添加成员");
            System.out.println("2. 修改成员信息");
            System.out.println("3. 查询成员信息");
            System.out.println("4. 移除成员");
            System.out.println("5. 退出系统");
            System.out.print("请输入你的选择：");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    modifyMember();
                    break;
                case 3:
                    queryMember();
                    break;
                case 4:
                    removeMember();
                    break;
                case 5:
                    System.out.println("感谢使用，再见！");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    // 添加成员
    private void addMember() {
        while (true) {
            System.out.print("请输入成员编号：");
            String id = scanner.nextLine();
            if (memberMap.containsKey(id)) {
                System.out.println("该编号已被占用，请重新输入。");
            } else {
                System.out.print("请输入成员姓名：");
                String name = scanner.nextLine();
                System.out.print("请输入成员年龄：");
                int age = scanner.nextInt();
                scanner.nextLine();
                System.out.print("请输入成员年级：");
                String grade = scanner.nextLine();
                Member member = new Member(id, name, age, grade);
                memberMap.put(id, member);
                System.out.println("成员添加成功！");
                break;
            }
        }
    }

    // 修改成员信息
    private void modifyMember() {
        System.out.print("请输入要修改的成员编号：");
        String id = scanner.nextLine();
        if (memberMap.containsKey(id)) {
            System.out.print("请输入修改后的年龄：");
            int age = scanner.nextInt();
            scanner.nextLine();
            System.out.print("请输入修改后的年级：");
            String grade = scanner.nextLine();
            Member member = memberMap.get(id);
            member.setAge(age);
            member.setGrade(grade);
            System.out.println("成员信息修改成功！");
        } else {
            System.out.println("成员编号不存在。");
        }
    }

    // 查询成员信息
    private void queryMember() {
        System.out.println("1. 查询所有成员");
        System.out.println("2. 按年级查询成员");
        System.out.print("请输入你的选择：");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            if (memberMap.isEmpty()) {
                System.out.println("社团中没有成员。");
            } else {
                System.out.println("=== 所有成员信息 ===");
                for (Member member : memberMap.values()) {
                    System.out.println("编号：" + member.getId() + "，姓名：" + member.getName() + "，年龄：" + member.getAge() + "，年级：" + member.getGrade());
                }
            }
        } else if (choice == 2) {
            System.out.print("请输入要查询的年级：");
            String grade = scanner.nextLine();
            boolean found = false;
            System.out.println("=== " + grade + " 年级成员信息 ===");
            for (Member member : memberMap.values()) {
                if (member.getGrade().equals(grade)) {
                    System.out.println("编号：" + member.getId() + "，姓名：" + member.getName() + "，年龄：" + member.getAge() + "，年级：" + member.getGrade());
                    found = true;
                }
            }
            if (!found) {
                System.out.println("社团中没有该年级的成员。");
            }
        } else {
            System.out.println("无效的选择。");
        }
    }

    // 移除成员
    private void removeMember() {
        System.out.print("请输入要移除的成员编号：");
        String id = scanner.nextLine();
        if (memberMap.containsKey(id)) {
            memberMap.remove(id);
            System.out.println("成员移除成功！");
        } else {
            System.out.println("成员编号不存在。");
        }
    }
}