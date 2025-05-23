package task02;

import java.sql.*;
import java.util.Scanner;

public class MilepostService {

    public Connection getConnection() throws Exception {
        String password = "123456";
        String user = "root";
        String url = "jdbc:mysql://localhost:3306/space";
        return DriverManager.getConnection(url, user, password);
    }

    public void addMilepost(Scanner sc) {
        try (Connection conn = getConnection()) {
            System.out.println("请输入里程碑名称：");
            String name = sc.nextLine();
            System.out.println("发射时间（格式：yyyy-MM-dd）：");
            String date = sc.nextLine();
            System.out.println("里程碑描述：");
            String depict = sc.nextLine();
            System.out.println("里程碑状态（0或1）：");
            int state = Integer.parseInt(sc.nextLine());

            String sql = "INSERT INTO milepost(name, launchtime, depict, state) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, depict);
            ps.setInt(4, state);
            ps.executeUpdate();

            System.out.println("新增里程碑信息成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void queryMileposts(Scanner sc) {
        try (Connection conn = getConnection()) {
            System.out.println("请输入需要查询的里程碑名称，输入 0 则查询所有里程碑：");
            String input = sc.nextLine();

            String sql;
            PreparedStatement ps;
            if ("0".equals(input)) {
                sql = "SELECT * FROM milepost";
                ps = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM milepost WHERE name LIKE ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + input + "%");
            }

            ResultSet rs = ps.executeQuery();
            boolean found = false;
            while (rs.next()) {
                Milepost m = new Milepost();
                m.id = rs.getInt("id");
                m.name = rs.getString("name");
                m.launchtime = rs.getString("launchtime");
                m.depict = rs.getString("depict");
                m.state = rs.getInt("state");
                System.out.println(m);
                found = true;
            }

            if (!found) {
                System.out.println("未查询到任何里程碑记录！");
            }
        } catch (Exception e) {
            System.out.println("查询失败：" + e.getMessage());
        }
    }

    public void editMilepost(Scanner sc) {
        try (Connection conn = getConnection()) {
            System.out.println("请输入要修改的里程碑ID：");
            int id = Integer.parseInt(sc.nextLine());

            System.out.println("请输入新的里程碑名称：");
            String name = sc.nextLine();
            System.out.println("发射时间（yyyy-MM-dd）：");
            String date = sc.nextLine();
            System.out.println("描述：");
            String depict = sc.nextLine();
            System.out.println("状态（0或1）：");
            int state = Integer.parseInt(sc.nextLine());

            String sql = "UPDATE milepost SET name=?, launchtime=?, depict=?, state=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, depict);
            ps.setInt(4, state);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("编辑里程碑成功！");
            } else {
                System.out.println("未找到该ID的里程碑！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMilepost(Scanner sc) {
        try (Connection conn = getConnection()) {
            System.out.println("请输入要删除的里程碑ID：");
            int id = Integer.parseInt(sc.nextLine());

            String sql = "DELETE FROM milepost WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("删除成功！");
            } else {
                System.out.println("未找到该ID的记录！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
