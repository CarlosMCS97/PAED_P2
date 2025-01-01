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

    public void branchAndBound(Task[] tasks, Intern[] interns, int numTasks) {
        PriorityQueue<BNBConfig> queue = new PriorityQueue<>();

        BNBConfig first = new BNBConfig(numTasks);
        queue.offer(first);
        float minCost = Float.MAX_VALUE;
        BNBConfig bestSolution = null;

        while (!queue.isEmpty()) {
            BNBConfig current = queue.poll();

            if (current.internIndex < interns.length) {
                List<BNBConfig> children = current.expand(tasks,interns,this);

                for (BNBConfig child : children) {
                    //updating the bound
                    child.bound = child.estimatedCost(tasks, interns, this);

                    if (child.bound < minCost) {
                        queue.offer(child);

                        if (allTasksAssigned(child.assigned) && child.currentCost < minCost) {
                            minCost = child.currentCost;
                            bestSolution = child;
                        }

                    }
                }

            }

        }

        System.out.println("Minimum Time: " + minCost);
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
