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

    public BNBConfig branchAndBound(Task[] tasks, Intern[] interns, int numTasks) {
        PriorityQueue<BNBConfig> queue = new PriorityQueue<>(); // Min-priority queue

        // Initial configuration
        BNBConfig first = new BNBConfig(numTasks);
        first.bound = first.estimatedCost(tasks, interns, this);
        queue.offer(first);

        float minCost = Float.MAX_VALUE;
        BNBConfig bestSolution = null;

        while (!queue.isEmpty()) {
            BNBConfig current = queue.poll(); // Get the next configuration

            // Expand current node
            List<BNBConfig> children = current.expand(tasks, interns, this);

            for (BNBConfig child : children) {
                // Update bound for child
                child.bound = child.estimatedCost(tasks, interns, this);

                // Prune branches where the estimate exceeds the current minimum
                if (child.bound < minCost) {
                    if (allTasksAssigned(child.assigned)) { // Check if all tasks are assigned
                        if (child.currentCost < minCost) { // Update the best solution
                            minCost = child.currentCost;
                            bestSolution = child;
                        }
                    } else {
                        queue.offer(child); // Continue exploring
                    }
                }
            }
        }

        System.out.println("Minimum Time: " + minCost);


        return bestSolution; // Return the best configuration found
    }


    private boolean allTasksAssigned(boolean[] assigned) {
        for (boolean taskAssigned : assigned) {
            if (!taskAssigned) {
                return false;
            }
        }
        return true;
    }

    public void greedyTO() {

    }

    public void greedyEQ() {

    }
}
