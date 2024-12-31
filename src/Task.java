import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {
    private String name;
    private String limit;
    private int time;
    private int difficulty;
    private int progress;
    private String importance;
    private String building;

    public Task(String name, String limit, int time, int difficulty, int progress, String importance, String building) {
        this.name = name;
        this.limit = limit;
        this.time = time;
        this.difficulty = difficulty;
        this.progress = progress;
        this.importance = importance;
        this.building = building;
    }

    // Getters and toString() method for displaying task information
    public String getName() { return name; }
    public String getLimit() { return limit; }
    public int getTime() { return time; }
    public int getDifficulty() { return difficulty; }
    public int getProgress() { return progress; }
    public String getImportance() { return importance; }
    public String getBuilding() { return building; }

    public String getTaskDetails() {
        return String.format("%s;%s;%d;%d;%d;%s;%s",
                getName(), getLimit(), getTime(), getDifficulty(), getProgress(), getImportance(), getBuilding());
    }

}
