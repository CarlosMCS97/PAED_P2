import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Algorithms {

    public float timeCalculation(Intern intern, Task task) {
        float time = 0;
        float avg = intern.getAverage();
        float skillPoints = 0;
        float taskTime = task.getTime();

        if (avg >= 5 && avg < 7) {
            skillPoints = avg * 10;
        }
        else {
            if (avg >= 7 && avg < 9) {
                skillPoints = (float) (avg * 10 * 1.2);
            }
            else {
                if (avg >= 9) {
                    skillPoints = (float) (avg * 10 * 1.5);
                }
            }
        }

        if (intern.isJunior()) {
            skillPoints += 10;
        }

        if (intern.getSubject().equals(task.getSubject())) {
            skillPoints += 20;
        }

        //Ensure skillPoints is greater than 0 to avoid division by zero
        if (skillPoints == 0) {
            throw new IllegalArgumentException("Skill points is 0.");
        }

        time = (taskTime / skillPoints) * 80;

        return time;
    }


    public void bruteForce() {

    }

    public void backtracking() {

    }

    public List<Integer> branchAndBound(Task[] tasks, Intern[] interns) {
        PriorityQueue<BNBConfig> queue = new PriorityQueue<>(); // Min-priority queue

        // Initial configuration
        BNBConfig first = new BNBConfig(new ArrayList<>(), 0, Arrays.asList(tasks));
        queue.offer(first);

        double minCost = Double.MAX_VALUE;
        BNBConfig bestSolution = null;

        while (!queue.isEmpty()) {
            BNBConfig current = queue.poll(); // Get the next configuration

            // Expand current node
            List<BNBConfig> children = current.expand(tasks, interns, this);

            for (BNBConfig child : children) {
                // Prune branches where the estimate exceeds the current minimum
                if (child.estimatedCost(tasks, interns, this) < minCost) {
                    if (child.remainingTasks.isEmpty()) { // Check if all tasks are assigned
                        if (child.getCost() < minCost) { // Update the best solution
                            minCost = child.getCost();
                            bestSolution = child;
                        }
                    } else {
                        queue.offer(child); // Continue exploring
                    }
                }
            }
        }

        return bestSolution != null ? bestSolution.assignments : null;
    }

    public boolean isValid(Intern intern, Task task, List<Integer> assignments, List<Task> assignedTasks) {
        int internIndex = assignments.size();

        // Calculate total difficulty for the intern
        int totalDifficulty = 0;
        for (int i = 0; i < assignments.size(); i++) {
            if (assignments.get(i) == internIndex) {
                totalDifficulty += assignedTasks.get(i).getDifficulty();
                if (!intern.getSubject().equals(task.getSubject())) return false;
            }
        }

        // Check junior intern difficulty constraint
        if (intern.isJunior() && totalDifficulty + task.getDifficulty() > 40) {
            return false;
        }
        return true;
    }

    public void greedyTO() {

    }

    public void greedyEQ() {

    }
}
