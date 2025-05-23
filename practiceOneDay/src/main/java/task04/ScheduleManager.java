package task04;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ScheduleManager {
    static class Schedule {
        String title;
        LocalDateTime eventTime;
        LocalDateTime createdTime;

        public Schedule(String title, LocalDateTime eventTime) {
            this.title = title;
            this.eventTime = eventTime;
            this.createdTime = LocalDateTime.now();
        }

        public String getDistanceFromNow() {
            Duration duration = Duration.between(LocalDateTime.now(), eventTime);
            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;
            return days + "天" + hours + "小时" + minutes + "分钟";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Schedule> schedules = new ArrayList<>();

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        while (true) {
            System.out.println("\n日程安排管理程序：");
            System.out.println("1. 创建日程安排  2. 查看日程安排");
            System.out.print("请输入操作编号：");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 读取换行

            if (choice == 1) {
                while (schedules.size() < 20) {
                    System.out.print("请输入标题：");
                    String title = scanner.nextLine();

                    System.out.print("请输入日期（格式 yyyy-MM-dd）：");
                    String dateStr = scanner.nextLine();

                    System.out.print("请输入时间（格式 HH:mm）：");
                    String timeStr = scanner.nextLine();

                    try {
                        LocalDate date = LocalDate.parse(dateStr, dateFmt);
                        LocalTime time = LocalTime.parse(timeStr, timeFmt);
                        LocalDateTime eventDateTime = LocalDateTime.of(date, time);

                        if (eventDateTime.isBefore(LocalDateTime.now())) {
                            System.out.println("错误：不能创建早于当前时间的日程！");
                            continue;
                        }

                        schedules.add(new Schedule(title, eventDateTime));
                        System.out.print("日程已创建！是否继续创建？（y/n）：");
                        String more = scanner.nextLine();
                        if (more.equalsIgnoreCase("n")) break;

                    } catch (Exception e) {
                        System.out.println("输入有误，请重新输入！");
                    }
                }
                System.out.println("创建日程安排完成！");
            } else if (choice == 2) {
                if (schedules.isEmpty()) {
                    System.out.println("暂无日程安排！");
                } else {
                    schedules.sort(Comparator.comparing(s -> s.eventTime));
                    System.out.printf("%-20s %-10s %-8s %-12s %-25s\n",
                            "日程标题", "日期", "时间", "距离现在", "创建时间");
                    for (Schedule s : schedules) {
                        System.out.printf("%-20s %-10s %-8s %-12s %-25s\n",
                                s.title,
                                s.eventTime.toLocalDate(),
                                s.eventTime.toLocalTime(),
                                s.getDistanceFromNow(),
                                s.createdTime.truncatedTo(ChronoUnit.MILLIS));
                    }
                }
            } else {
                System.out.println("输入无效，请重新选择！");
            }
        }
    }
}