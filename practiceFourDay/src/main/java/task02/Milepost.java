package task02;

public class Milepost {
    public int id;
    public String name;
    public String launchtime;
    public String depict;
    public int state;

    @Override
    public String toString() {
        return "Milepost{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", launchtime='" + launchtime + '\'' +
                ", depict='" + depict + '\'' +
                ", state=" + state +
                '}';
    }
}
