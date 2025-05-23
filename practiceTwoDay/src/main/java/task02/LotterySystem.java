package task02;

import java.util.*;

class LotterySystem {
    private Set<Customer> participatedCustomers = new HashSet<>();
    private List<Customer> currentRoundCustomers = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void startLottery() {
        System.out.println("===== 欢迎参加抽奖活动 =====");
        System.out.println("每次活动抽取3名幸运顾客");

        while (true) {
            System.out.println("\n当前参与人数: " + currentRoundCustomers.size() + "/5");
            System.out.print("请输入您的姓名和手机号（格式：姓名 手机号），输入q退出: ");
            String input = scanner.nextLine();

            if ("q".equalsIgnoreCase(input)) {
                System.out.println("感谢参与，活动结束！");
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("输入格式错误，请重新输入（格式：姓名 手机号）。");
                continue;
            }

            String name = parts[0];
            String phone = parts[1];

            if (!phone.matches("\\d{11}")) {
                System.out.println("手机号格式错误，请输入11位数字。");
                continue;
            }

            Customer customer = new Customer(name, phone);
            if (participatedCustomers.contains(customer)) {
                System.out.println("您已参与过抽奖，不能重复参与。");
                continue;
            }

            participatedCustomers.add(customer);
            currentRoundCustomers.add(customer);
            System.out.println("您成功参与本次抽奖活动！");

            if (currentRoundCustomers.size() == 5) {
                startRoundLottery();
            }
        }
    }

    private void startRoundLottery() {
        System.out.println("\n===== 启动第" + (participatedCustomers.size() / 5) + "轮抽奖活动 =====");

        List<Customer> candidates = new ArrayList<>(currentRoundCustomers);
        List<Customer> winners = new ArrayList<>();
        Random random = new Random();

        // 抽取3名获奖者
        while (winners.size() < Math.min(3, candidates.size())) {
            int index = random.nextInt(candidates.size());
            winners.add(candidates.remove(index));
        }

        // 按姓名升序排序，如果姓名相同则按手机号尾号排序
        winners.sort(Comparator.comparing(Customer::getName)
                .thenComparing(c -> c.getPhone().substring(9)));

        System.out.println("------ 本次中奖顾客名单 ------");
        for (Customer winner : winners) {
            System.out.printf("姓名: %-8s 手机尾号: %s%n",
                    winner.getName(),
                    winner.getPhone().substring(7));
        }

        // 清空当前轮次参与者
        currentRoundCustomers.clear();
    }
}