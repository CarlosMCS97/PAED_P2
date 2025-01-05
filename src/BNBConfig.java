import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BNBConfig implements Comparable<BNBConfig> {

    List<Integer> assignments;
    double cost;
    List<Task> remainingTasks;


    public BNBConfig(List<Integer> assignments, double cost, List<Task> remainingTasks) {
        this.assignments = new ArrayList<>(assignments);
        this.cost = cost;
        this.remainingTasks = remainingTasks;
    }

    public BNBConfig(BNBConfig that) {
        this.assignments = that.assignments;
        this.cost = that.cost;
        this.remainingTasks = that.remainingTasks;
    }

    @Override
    public int compareTo(BNBConfig other) {
        return Double.compare(this.cost, other.cost);
    }

    public double getCost() {
        return cost;
    }

    public List<BNBConfig> expand(Task[] tasks, Intern[] interns, Algorithms algorithm) {
        List<BNBConfig> children = new ArrayList<>();

        // Get the next task to assign
        Task task = remainingTasks.get(0);
        List<Task> newRemainingTasks = remainingTasks.subList(1, remainingTasks.size());

        for (int i = 0; i < interns.length; i++) {
            Intern intern = interns[i];

            // Check Building Constraint
            boolean sameBuilding = true;
            for (int j = 0; j < assignments.size(); j++) {
                if (assignments.get(j) == i) { // Intern has been assigned other tasks
                    Task assignedTask = tasks[j];
                    if (!assignedTask.getBuilding().equals(task.getBuilding())) {
                        sameBuilding = false;
                        break;
                    }
                }
            }

            if (!sameBuilding) {
                continue; // Skip this intern if they violate the building constraint
            }

            // Check Junior Difficulty Constraint
            int totalDifficulty = 0;
            for (int j = 0; j < assignments.size(); j++) {
                if (assignments.get(j) == i) { // Tasks assigned to this intern
                    totalDifficulty += tasks[j].getDifficulty();
                }
            }

            if (intern.isJunior() && (totalDifficulty + task.getDifficulty() > 40)) {
                continue; // Skip this intern if the difficulty exceeds the limit
            }

            // Calculate time and create a new configuration
            double time = intern.calculateTime(task);
            List<Integer> newAssignments = new ArrayList<>(assignments);
            newAssignments.add(i);

            // Add new configuration
            children.add(new BNBConfig(newAssignments, cost + time, newRemainingTasks));
        }
        return children;
    }

    public double estimatedCost(Task[] tasks, Intern[] interns, Algorithms algorithm) {
        double estimate = cost;

        // Convert Task[] to List<Task> for compatibility with isValid method
        List<Task> taskList = Arrays.asList(tasks);

        for (Task task : remainingTasks) {
            double minTime = Double.MAX_VALUE;

            for (int i = 0; i < interns.length; i++) {
                Intern intern = interns[i];

                // Use isValid method, passing taskList instead of Task[]
                if (algorithm.isValid(intern, task, assignments, taskList)) {
                    double time = intern.calculateTime(task);
                    minTime = Math.min(minTime, time); // Take the minimum valid time
                }
            }

            estimate += minTime; // Add the minimum time for this task
        }

        return estimate;
    }



}
