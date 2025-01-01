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
        PriorityQueue<BNBConfig> queue = new PriorityQueue<>();

        BNBConfig first = new BNBConfig(numTasks);
        first.bound = first.estimatedCost(tasks, interns, this); // Use estimatedCost for initial bound
        queue.offer(first);
        float minCost = Float.MAX_VALUE;
        BNBConfig bestSolution = first;

        while (!queue.isEmpty()) {
            BNBConfig current = queue.poll();

            if (current.internIndex < interns.length) {
                List<BNBConfig> children = current.expand(tasks,interns,this); //Branch

                for (BNBConfig child : children) {
                    //updating the bound which is the estimate
                    child.bound = child.estimatedCost(tasks, interns, this);

                    if (child.internIndex < interns.length) { //if child has more levels left
                        if (child.bound < minCost) { //(BOUND) Prune if bound exceeds minCost
                            queue.offer(child);
                        }
                    }
                    else {
                        //if no more levels, evaluate solution
                        if (child.currentCost < minCost && allTasksAssigned(child.assigned)) {
                            minCost = child.currentCost;
                            bestSolution = child;
                        }
                    }
                }

            }

        }

        System.out.println("Minimum Time: " + minCost);
        return bestSolution;
    }

    private boolean allTasksAssigned(boolean[] assigned) {
        for (boolean task : assigned) {
            if (!task) {
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
