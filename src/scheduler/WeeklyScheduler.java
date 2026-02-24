package scheduler;

import model.Project;
import java.util.List;

public class WeeklyScheduler {

    public static void schedule(List<Project> projects) {

        // Sort projects by score (high to low)
        projects.sort((a, b) ->
                Double.compare(b.score, a.score));

        Project[] week = new Project[5];
        int totalRevenue = 0;

        // Assign projects to days
        for (Project p : projects) {
            int lastDay = Math.min(p.deadline, 5) - 1;

            for (int d = lastDay; d >= 0; d--) {
                if (week[d] == null) {
                    week[d] = p;
                    totalRevenue += p.revenue;
                    break;
                }
            }
        }

        // Print schedule
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        System.out.println("\n📅 WEEKLY SCHEDULE");

        for (int i = 0; i < 5; i++) {
            if (week[i] != null) {
                System.out.println(
                        days[i] + " → " +
                                week[i].title +
                                " (₹" + week[i].revenue + ")"
                );
            } else {
                System.out.println(days[i] + " → FREE");
            }
        }

        // Print total revenue
        System.out.println("\n💰 Total Revenue = ₹" + totalRevenue);
    }
}