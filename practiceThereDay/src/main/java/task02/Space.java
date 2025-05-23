package task02;

public class Space {
    private int id;
    private String name;

    public Space() {
    }

    public Space(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Space{id=" + id + ", name=\"" + name + "\"}";
    }
}