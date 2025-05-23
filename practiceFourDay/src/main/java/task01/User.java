package task01;

public class User {
    public String id;
    public String name;
    public String password;
    public boolean voted;

    public User(String id, String name, String password, boolean voted) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.voted = voted;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + password + "," + voted;
    }

    public static User fromString(String line) {
        String[] parts = line.split(",");
        return new User(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]));
    }
}