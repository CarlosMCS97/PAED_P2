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

        // Explore all possible assignments for the next unassigned task
        for (int i = 0; i < tasks.length; i++) {
            if (!assigned[i]) { // Task not yet assigned
                for (int j = 0; j < interns.length; j++) { // Try every intern
                    Intern intern = interns[j];
                    Task task = tasks[i];

                    // Constraints
                    if (intern.isJunior() && (difficulty + task.getDifficulty()) > 40) {
                        continue; // Skip invalid assignment due to difficulty
                    }

                    if (building != null && !building.equals(task.getBuilding())) {
                        continue; // Skip invalid assignment due to building constraint
                    }

                    // Create a child configuration
                    BNBConfig child = new BNBConfig(this);
                    float time = algorithm.timeCalculation(intern, task);

                    // Update child configuration
                    child.currentCost += time;
                    child.assigned[i] = true;

                    if (intern.isJunior()) {
                        child.difficulty += task.getDifficulty();
                    }

                    child.building = task.getBuilding();
                    child.internIndex++; // Move to the next level

                    children.add(child); // Add child to the list
                }
            }
        }

        return children;
    }


    public float estimatedCost(Task[] tasks, Intern[] interns, Algorithms algorithm) {
        float estimate = currentCost; // Start with current cost

        // Add remaining tasks based on the lowest feasible time
        for (int i = 0; i < tasks.length; i++) {
            if (!assigned[i]) { // Task not yet assigned
                float minTime = Float.MAX_VALUE;

                for (int j = 0; j < interns.length; j++) { // Try every intern
                    Intern intern = interns[j];
                    Task task = tasks[i];

                    // Check constraints
                    if (intern.isJunior() && (difficulty + task.getDifficulty()) > 40) {
                        continue; // Skip invalid assignment
                    }

                    if (building != null && !building.equals(task.getBuilding())) {
                        continue; // Skip invalid assignment
                    }

                    // Calculate time required for this intern-task pair
                    float time = algorithm.timeCalculation(intern, task);
                    minTime = Math.min(minTime, time); // Find the fastest valid time
                }

                // Add the lowest cost for this task
                estimate += minTime;
            }
        }

        return estimate; // Total estimate (current cost + remaining cost)
    }


}
