import java.util.*;

public class Algorithms {

    /*
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
*/

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
    /*
    public void greedyTO(Task[] tasks, Intern[] interns, int numTasks) {
        // Step 1: Sort tasks based on importance or time (a greedy criterion)
        Arrays.sort(tasks, (a, b) -> {
            // Sorting by importance (for example, importance can be time taken or progress)
            return Integer.compare(b.getTime(), a.getTime());
        });

        // Step 2: Assign tasks to interns based on greedy principles
        boolean[] taskAssigned = new boolean[tasks.length];
        float totalTime = 0;

        for (Intern intern : interns) {
            for (int i = 0; i < tasks.length && numTasks > 0; i++) {
                if (!taskAssigned[i]) {
                    Task task = tasks[i];
                    // Ensure intern can handle the task based on restrictions
                    if (intern.isJunior() && task.getDifficulty() > 40) {
                        continue; // Skip if difficulty too high for junior intern
                    }

                    // Assign task to intern
                    float taskTime = timeCalculation(intern, task);
                    System.out.println("Assigning Task: " + task.getName() + " to Intern: " + intern.getName());
                    System.out.println("Estimated Time: " + taskTime + " minutes");

                    totalTime += taskTime;
                    taskAssigned[i] = true; // Mark task as assigned
                    numTasks--; // Decrease the count of tasks left to assign

                    if (numTasks == 0) {
                        break; // Exit loop if all tasks are assigned
                    }
                }
            }
        }

        System.out.println("Total Time for Greedy Task Organization: " + totalTime + " minutes");
    }


     */
    public void greedyTO(Task[] tasks, Intern[] interns, int numTasks) {
        // Step 1: Sort tasks based on importance (time, difficulty, or a heuristic combination)
        Arrays.sort(tasks, (a, b) -> Integer.compare(b.getTime(), a.getTime()));

        // Step 2: Initialize tracking variables
        boolean[] taskAssigned = new boolean[tasks.length];
        String[] internBuilding = new String[interns.length]; // Track building for each intern
        float[] internWorkload = new float[interns.length];  // Track total workload for each intern
        float totalTime = 0;

        System.out.println("Assigning tasks using a balanced greedy heuristic...");

        for (int t = 0; t < tasks.length && numTasks > 0; t++) {
            Task task = tasks[t];
            float minAdjustedTime = Float.MAX_VALUE;
            int bestInternIndex = -1;

            // Find the best intern for the current task
            for (int i = 0; i < interns.length; i++) {
                Intern intern = interns[i];

                // Skip if this task is too difficult for a junior intern
                if (intern.isJunior() && task.getDifficulty() > 40) {
                    continue;
                }

                // Enforce building constraint: Only allow if intern has no building or matches the task's building
                if (internBuilding[i] != null && !internBuilding[i].equals(task.getBuilding())) {
                    continue;
                }

                // Calculate adjusted time (original time + workload balancing factor)
                float taskTime = timeCalculation(intern, task);
                float adjustedTime = taskTime + internWorkload[i]; // Balance load

                if (adjustedTime < minAdjustedTime) {
                    minAdjustedTime = adjustedTime;
                    bestInternIndex = i;
                }
            }

            // Assign the task to the best intern
            if (bestInternIndex != -1) {
                Intern bestIntern = interns[bestInternIndex];
                System.out.println("Task: " + task.getName() + " assigned to " + bestIntern.getName());
                System.out.println("Estimated time: " + minAdjustedTime + " minutes");

                totalTime += minAdjustedTime;
                internWorkload[bestInternIndex] += minAdjustedTime; // Update intern's workload
                internBuilding[bestInternIndex] = task.getBuilding(); // Update building for the intern
                taskAssigned[t] = true;
                numTasks--; // Reduce remaining tasks to assign
            } else {
                System.out.println("Task: " + task.getName() + " could not be assigned due to constraints.");
            }
        }

        System.out.println("Total workload distributed. Total estimated time: " + totalTime + " minutes");
        printInternWorkloads(interns, internWorkload);
    }



    public float timeCalculation(Intern intern, Task task) {
        float skillPoints = intern.getAverage() * 10;
        if (intern.getAverage() >= 7 && intern.getAverage() < 9) {
            skillPoints *= 1.2f;
        } else if (intern.getAverage() >= 9) {
            skillPoints *= 1.5f;
        }
        if (intern.isJunior()) {
            skillPoints += 10;
        }
        if (intern.getSubject().equals(task.getSubject())) {
            skillPoints += 20;
        }

        if (skillPoints == 0) {
            throw new IllegalArgumentException("Skill points cannot be zero.");
        }

        return (task.getTime() / skillPoints) * 80;
    }

    private void printInternWorkloads(Intern[] interns, float[] internWorkload) {
        System.out.println("\nIntern Workloads:");
        for (int i = 0; i < interns.length; i++) {
            System.out.printf("%s - Total Workload: %.2f minutes%n", interns[i].getName(), internWorkload[i]);
        }
    }




    public void greedyEQ() {

    }
}
