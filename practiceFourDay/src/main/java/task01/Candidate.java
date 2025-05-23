package task01;

public class Candidate {
    public String name;
    public double score;
    public String slogan;
    public int voteCount;

    public Candidate(String name, double score, String slogan) {
        this.name = name;
        this.score = score;
        this.slogan = slogan;
        this.voteCount = 0;
    }

    @Override
    public String toString() {
        return name + "," + score + "," + slogan + "," + voteCount;
    }

    public static Candidate fromString(String line) {
        String[] parts = line.split(",");
        Candidate c = new Candidate(parts[0], Double.parseDouble(parts[1]), parts[2]);
        if (parts.length >= 4) c.voteCount = Integer.parseInt(parts[3]);
        return c;
    }
}