import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Functions {
    private Task[] tasks; // Store tasks in an array
    private int nTasks;

    private Intern[] interns; //Store interns in an array
    private int nInterns;

    public Functions() {
        this.tasks = null; // Initialize the list here
        this.nTasks = 0;
        this.interns = null;
        this.nInterns = 0;
    }

    //function to read any file and storage the information into an array
    public void readTasksFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // Read the number of tasks (first line)
            if (scanner.hasNextLine()) {
                nTasks = Integer.parseInt(scanner.nextLine().trim());
                tasks = new Task[nTasks]; // Initialize the array with the given size
            }

            // Read each task line
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yy");
            int index = 0;

            while (scanner.hasNextLine() && index < nTasks) {
                String line = scanner.nextLine();
                String[] fields = line.split(";");

                if (fields.length == 7) {
                    String name = fields[0];
                    String limit = fields[1];
                    int time = Integer.parseInt(fields[2]);
                    int difficulty = Integer.parseInt(fields[3]);
                    int progress = Integer.parseInt(fields[4]);
                    String importance = fields[5];
                    String building = fields[6];

                    tasks[index] = new Task(name, limit, time, difficulty, progress, importance, building);
                    index++;
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing task count or task fields: " + e.getMessage());
        }
    }

    //function to set the list with the selected file by the user
    public void getFile(int option) {
        switch (option) {
            case 1:
                readTasksFile("data/tasks.paed");
                break;
            case 2:
                readTasksFile("data/tasksExtraSmall.paed");
                break;
            case 3:
                readTasksFile("data/tasksSmall.paed");
                break;
        }
    }

    //function to set the list with the selected file by the user
    public void getTasksFile(int option) {
        switch (option) {
            case 1:
                readTasksFile("data/tasks.paed");
                break;
            case 2:
                readTasksFile("data/tasksExtraSmall.paed");
                break;
            case 3:
                readTasksFile("data/tasksSmall.paed");
                break;
        }
    }

    //function to set the list with the selected file by the user
    public void getInternsFile(int option) {
        switch (option) {
            case 1:
                readTasksFile("data/interns.paed");
                break;
            case 2:
                readTasksFile("data/internsExtraSmall.paed");
                break;
            case 3:
                readTasksFile("data/internsSmall.paed");
                break;
        }
    }

    // Function to delete the current array
    public void deleteData() {
        // Clear each element in the array
        Arrays.fill(tasks, null);
        tasks = new Task[0]; // Reset the array size to 0
    }

    // Function to return the actual size of the tasks array
    public int getnTasks() {
        return tasks.length;
    }

    // Function to return the tasks array
    public Task[] getTasks() {
        return tasks;
    }

    public static void printTaskArray(Task[] taskArray, int numTasks) {
        for (int i = 0; i < numTasks; i++) {
            if (taskArray!= null) {
                System.out.println(taskArray[i].getTaskDetails());
            }
        }
    }

    public void taskOrganization() {
        Algorithms algorithms = new Algorithms();
        Menu menu = new Menu();

        System.out.println("\nSelect type of data file: ");
        System.out.println("\t1. tasks");
        System.out.println("\t2. tasksExtraSmall");
        System.out.println("\t3. tasksSmall");

        int file = menu.inputScanner(1, 3, "file");
        getTasksFile(file);

        System.out.println(getnTasks() + " in the array");

        System.out.println("\nSelect the problem to solve: ");
        System.out.println("\t1. Task Organization");
        System.out.println("\t2. Equitable distribution");

        int option = menu.inputScanner(1, 2 , "problem");

        //Asking the number of task
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of tasks: ");
        int numTasks = scanner.nextInt(); // Number of tasks to sort
        System.out.println("You choose " + numTasks + " tasks.");

        long startTime = 0;
        long endTime = 0;
        long totalDuration = 0;

        if (option == 1) {
            startTime = System.currentTimeMillis();
            algorithms.insertionSort(tasks, numTasks);
            endTime = System.currentTimeMillis();
            totalDuration = (endTime - startTime);

            System.out.println("Selection Sorting completed in " + totalDuration+ " ms");
        }
        else if (option == 2) {

            startTime = System.nanoTime();
            algorithms.selectionSort(tasks, numTasks);
            endTime = System.nanoTime();
            totalDuration = (endTime - startTime)/1000000;

            System.out.println("Selection Sorting completed in " + totalDuration+ " ms");
        }

        // Ask if user wants to see the sorted tasks
        System.out.println("\nDo you want to see the shorted information? (y/n) ");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        if (answer.equals("y")) {
            printTaskArray(tasks,numTasks);
        }

        deleteData();
        System.out.println(getnTasks() + " in the array");
    }

    public void equitableDistribution(){
        Menu menu = new Menu();
        Algorithms algorithms = new Algorithms();


        System.out.println("\nSelect type of data file: ");
        System.out.println("\t1. Random");
        System.out.println("\t2. Random Small");
        int file = menu.inputScanner(1, 5, "file");
        getInternsFile(file);

        //Asking the number of task to sort
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of tasks to sort: ");
        int numTasks = scanner.nextInt(); // The value is assigned to the nTasks
        System.out.println("You chose to sort " + numTasks + " tasks.");

        System.out.println("\nSelect type of sorting algorithm: ");
        System.out.println("\t1. Quick Sort");
        System.out.println("\t2. Merge Sort");
        int option = menu.inputScanner(1, 2, "sorting algorithm");

        // Create a subarray of the first numTasks elements
        Task[] subArray = new Task[numTasks];
        System.arraycopy(tasks, 0, subArray, 0, numTasks);

        if (option == 1) {
            long startTime = System.currentTimeMillis();
            algorithms.quickSort(subArray, 0,subArray.length - 1);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime ;
            System.out.println("\nQuick Sort completed in " + duration + " ms");

        }
        else if (option == 2) {
            long startTime = System.currentTimeMillis();
            algorithms.mergeSort(subArray);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime ;
            System.out.println("\nMerge Sort completed in " + duration + " ms");

        }

        System.out.println("\nDo you want to see the shorted information? (y/n) ");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        if (answer.equals("y")) {
            printTaskArray(tasks,numTasks);
        }

        deleteData();

    }

    public void sortingPerformanceAnalysis(){

    }


}
