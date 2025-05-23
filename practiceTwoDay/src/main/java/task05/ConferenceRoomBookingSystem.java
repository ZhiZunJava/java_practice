package task05;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class ConferenceRoomBookingSystem {
    private List<ConferenceRoom> conferenceRooms = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ConferenceRoomBookingSystem() {
        initializeConferenceRooms();
    }

    private void initializeConferenceRooms() {
        conferenceRooms.add(new ConferenceRoom("会议室A102", 20, true));
        conferenceRooms.add(new ConferenceRoom("会议室A305", 15, true));
        conferenceRooms.add(new ConferenceRoom("会议室B201", 50, true));
        conferenceRooms.add(new ConferenceRoom("会议室B303", 10, false));
        conferenceRooms.add(new ConferenceRoom("会议室C101", 20, false));
    }

    public void displayMenu() {
        while (true) {
            System.out.println("--------会议室预订系统--------");
            System.out.println("1. 查看所有会议室");
            System.out.println("2. 预订会议室");
            System.out.println("3. 查看预订信息");
            System.out.println("4. 查看指定时间段可用会议室");
            System.out.println("5. 退出系统");
            System.out.print("请输入您选择的操作: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    displayAllConferenceRooms();
                    break;
                case 2:
                    bookConferenceRoom();
                    break;
                case 3:
                    displayReservationInfo();
                    break;
                case 4:
                    displayAvailableConferenceRooms();
                    break;
                case 5:
                    System.out.println("感谢使用，再见！");
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    private void displayAllConferenceRooms() {
        System.out.println("--------所有会议室列表--------");
        System.out.println("名称\t\t容纳人数\t多媒体设备");
        for (ConferenceRoom room : conferenceRooms) {
            System.out.println(room.getName() + "\t\t" + room.getCapacity() + "\t\t" + (room.hasMultimedia()? "有" : "无"));
        }
    }

    private void bookConferenceRoom() {
        displayAllConferenceRooms();
        System.out.print("请选择要预订的会议室: ");
        String roomName = scanner.nextLine();
        ConferenceRoom selectedRoom = null;
        for (ConferenceRoom room : conferenceRooms) {
            if (room.getName().equals(roomName)) {
                selectedRoom = room;
                break;
            }
        }
        if (selectedRoom == null) {
            System.out.println("无效的会议室选择，请重新输入。");
            return;
        }
        System.out.print("请选择预订日期 (yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        System.out.print("请选择预订时间段 (例如: 09:00-10:00): ");
        String timeStr = scanner.nextLine();
        String[] timeParts = timeStr.split("-");
        LocalDateTime start = LocalDateTime.parse(dateStr + " " + timeParts[0] + ":00", dateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(dateStr + " " + timeParts[1] + ":00", dateTimeFormatter);
        if (selectedRoom.reserve(start, end)) {
            System.out.println("预订成功！");
        } else {
            System.out.println("该时间段该会议室已被预订，请选择其他时间段或其他会议室！");
        }
    }

    private void displayReservationInfo() {
        System.out.println("--------所有预订信息--------");
        for (ConferenceRoom room : conferenceRooms) {
            System.out.println(room.getName());
            for (Map.Entry<LocalDateTime, LocalDateTime> entry : room.getReservations().entrySet()) {
                System.out.println("预订时间[" + entry.getKey().format(dateTimeFormatter) + "~" + entry.getValue().format(dateTimeFormatter) + "]");
            }
            if (room.getReservations().isEmpty()) {
                System.out.println("该会议室暂无预订！");
            }
        }
    }

    private void displayAvailableConferenceRooms() {
        System.out.print("请输入日期(yyyy-mm-dd): ");
        String dateStr = scanner.nextLine();
        System.out.print("请输入时间段(例如: 09:00-10:00): ");
        String timeStr = scanner.nextLine();
        String[] timeParts = timeStr.split("-");
        LocalDateTime start = LocalDateTime.parse(dateStr + " " + timeParts[0] + ":00", dateTimeFormatter);
        LocalDateTime end = LocalDateTime.parse(dateStr + " " + timeParts[1] + ":00", dateTimeFormatter);
        System.out.println("--------该时间段内可用会议室--------");
        System.out.println("名称\t\t容纳人数\t多媒体设备");
        for (ConferenceRoom room : conferenceRooms) {
            if (room.isAvailable(start, end)) {
                System.out.println(room.getName() + "\t\t" + room.getCapacity() + "\t\t" + (room.hasMultimedia()? "有" : "无"));
            }
        }
    }
}
