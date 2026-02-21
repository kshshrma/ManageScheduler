package model;

public class Project {
    public int id;
    public String title;
    public int deadline;
    public int revenue;
    public double score;

    public Project(int id, String title, int deadline, int revenue) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
        this.revenue = revenue;
    }
}