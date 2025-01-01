import java.util.ArrayList;
import java.util.List;

public class BNBConfig implements Comparable<BNBConfig> {

    int internIndex;
    boolean[] assigned;
    float currentCost;
    float bound;
    int difficulty;
    String building;

    public BNBConfig(int numTask) {
        this.internIndex = 0;
        this.assigned = new boolean[numTask];
        this.currentCost = 0;
        this.bound = 0;
        this.difficulty = 0;
        this.building = null;
    }

    public BNBConfig(BNBConfig that) {
        this.internIndex = that.internIndex;
        this.assigned = that.assigned.clone();
        this.currentCost = that.currentCost;
        this.bound = that.bound;
        this.difficulty = that.difficulty;
        this.building = that.building;
    }

    @Override
    public int compareTo(BNBConfig that) {
        return Float.compare(this.bound, that.bound);
    }

    public List<BNBConfig> expand(Task[] tasks, Intern[] interns, Algorithms algorithm) {
        List<BNBConfig> children = new ArrayList<>();

        for (int i = 0; i < tasks.length; i++) {
            if (!assigned[i]) {
                BNBConfig child = new BNBConfig(this);
                Intern intern = interns[internIndex];
                Task task = tasks[i];

                if (intern.isJunior() && (child.difficulty + task.getDifficulty()) > 40) {
                    continue;
                }

                if (child.building != null && !child.building.equals(task.getBuilding())) {
                    continue;
                }

                child.building = task.getBuilding();

                float time = algorithm.timeCalculation(intern, task);
                child.currentCost += time;
                child.assigned[i] = true;
                if (intern.isJunior()) {
                    child.difficulty += task.getDifficulty();
                }
                else {
                    child.difficulty += 0;
                }

                child.internIndex++;

                children.add(child);

            }
        }

        return children;
    }

    public float estimatedCost(Task[] tasks, Intern[] interns, Algorithms algorithm) {
        float estimate = currentCost;
        for (int i = internIndex; i < tasks.length; i++) {
            if (!assigned[i]) {
                Intern intern = interns[internIndex];
                Task task = tasks[i];
                estimate += algorithm.timeCalculation(intern, task);
            }
        }
        return estimate;
    }
}
