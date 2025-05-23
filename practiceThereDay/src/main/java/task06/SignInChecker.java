package task06;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignInChecker {
    public static void main(String[] args) {
        String inputPath = "src/main/java/task06/demo.txt";
        String outputPath = "src/main/java/task06/output.txt";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String deadlineStr = "2023-12-27 09:00:00";

        try {
            Date deadline = sdf.parse(deadlineStr);

            try (
                    BufferedReader reader = new BufferedReader(new FileReader(inputPath));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length != 3) continue;

                    String name = parts[0];
                    String timeStr = parts[1] + " " + parts[2];

                    Date signTime = sdf.parse(timeStr);
                    if (signTime.after(deadline)) {
                        writer.write(name + " " + timeStr);
                        writer.newLine();
                    }
                }
                System.out.println("迟到人员统计完成！");
            }
        } catch (Exception e) {
            System.out.println("处理失败：" + e.getMessage());
        }
    }
}