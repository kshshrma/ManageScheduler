package app;

import db.ProjectDAO;
import model.Project;
import predictor.RevenuePredictor;
import scheduler.ProjectScorer;
import scheduler.WeeklyScheduler;

import java.util.List;
import java.util.Scanner;

public class MainApp {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== TASK SCHEDULER MENU =====");
            System.out.println("1. Add a Task");
            System.out.println("2. View All Tasks");
            System.out.println("3. Delete Task (by ID)");
            System.out.println("4. Schedule Tasks (Day-wise)");
            System.out.println("5. Update Task (by ID)");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear input buffer

            try {
                switch (choice) {

                    case 1:
                        addTask();
                        break;

                    case 2:
                        viewTasks();
                        break;

                    case 3:
                        deleteTask();
                        break;

                    case 4:
                        scheduleTasks();
                        break;

                    case 5:
                        updateTask();
                        break;

                    case 6:
                        System.out.println("Exiting... Bye 👋");
                        System.exit(0);

                    default:
                        System.out.println("❌ Invalid choice. Try again.");
                }

            } catch (Exception e) {
                System.out.println("⚠ Error: " + e.getMessage());
            }
        }
    }

    // ================= OPTION 1 =================
    private static void addTask() throws Exception {

        System.out.print("Enter task title: ");
        String title = sc.nextLine();

        System.out.print("Enter deadline (days): ");
        int deadline = sc.nextInt();

        System.out.print("Enter revenue: ");
        int revenue = sc.nextInt();

        ProjectDAO.addProject(title, deadline, revenue);
        System.out.println("✅ Task added successfully");
    }

    // ================= OPTION 2 =================
    private static void viewTasks() throws Exception {

        List<Project> projects = ProjectDAO.getAllProjects();

        if (projects.isEmpty()) {
            System.out.println("⚠ No tasks found");
            return;
        }

        System.out.println("\nID | TITLE | DEADLINE | REVENUE");
        System.out.println("--------------------------------");

        for (Project p : projects) {
            System.out.println(
                    p.id + " | " +
                            p.title + " | " +
                            p.deadline + " | " +
                            p.revenue
            );
        }
    }

    // ================= OPTION 3 =================
    private static void deleteTask() throws Exception {

        System.out.print("Enter task ID to delete: ");
        int id = sc.nextInt();

        boolean deleted = ProjectDAO.deleteProject(id);

        if (deleted)
            System.out.println("✅ Task deleted successfully");
        else
            System.out.println("❌ Task ID not found");
    }

    // ================= OPTION 4 =================
    private static void scheduleTasks() throws Exception {

        List<Project> projects = ProjectDAO.getAllProjects();

        if (projects.isEmpty()) {
            System.out.println("⚠ No tasks to schedule");
            return;
        }

        // Dummy historical revenue data
        int[] lastWeeks = {60000, 70000, 80000};
        double futureAvg = RevenuePredictor.predict(lastWeeks);

        // Score each project
        for (Project p : projects) {
            p.score = ProjectScorer.score(p, futureAvg);
        }

        // Run scheduler
        WeeklyScheduler.schedule(projects);
    }

    // ================= OPTION 5 =================
    private static void updateTask() throws Exception {

        System.out.print("Enter task ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // clear buffer

        System.out.print("Enter new title: ");
        String title = sc.nextLine();

        System.out.print("Enter new deadline (days): ");
        int deadline = sc.nextInt();

        System.out.print("Enter new revenue: ");
        int revenue = sc.nextInt();

        boolean updated = ProjectDAO.updateProject(id, title, deadline, revenue);

        if (updated)
            System.out.println("✅ Task updated successfully");
        else
            System.out.println("❌ Task ID not found");
    }
}