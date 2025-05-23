package task01;

import java.io.*;
import java.util.*;

public class DataManager {
    public static List<User> loadUsers(String path) throws IOException {
        List<User> list = new ArrayList<>();
        File file = new File(path);
        if (!file.exists()) return list;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(User.fromString(line));
            }
        }
        return list;
    }

    public static List<Candidate> loadCandidates(String path) throws IOException {
        List<Candidate> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(Candidate.fromString(line));
            }
        }
        return list;
    }

    public static void saveUsers(String path, List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (User u : users) writer.write(u.toString() + "\n");
        }
    }

    public static void saveCandidates(String path, List<Candidate> candidates) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Candidate c : candidates) writer.write(c.toString() + "\n");
        }
    }

    public static void writeResult(List<Candidate> candidates, String path) throws IOException {
        candidates.sort((a, b) -> b.voteCount - a.voteCount);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("所有竞选人票数如下：\n");
            for (Candidate c : candidates) {
                writer.write("姓名：" + c.name + "，票数：" + c.voteCount + "\n");
            }
            writer.write("\n入选人员如下：\n");
            writer.write("姓名：" + candidates.get(0).name + "，票数：" + candidates.get(0).voteCount + "\n");
            writer.write("姓名：" + candidates.get(1).name + "，票数：" + candidates.get(1).voteCount + "\n");
        }
    }
}
