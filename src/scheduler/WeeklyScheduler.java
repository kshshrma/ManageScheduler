package scheduler;

import model.Project;
import java.util.*;

public class WeeklyScheduler {

    public static void schedule(List<Project> projects) {

        projects.sort((a, b) ->
                Double.compare(b.score, a.score));

        Project[] week = new Project[5];

        for (Project p : projects) {
            int lastDay = Math.min(p.deadline, 5) - 1;
            for (int d = lastDay; d >= 0; d--) {
                if (week[d] == null) {
                    week[d] = p;
                    break;
                }
            }
        }

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        for (int i = 0; i < 5; i++) {
            System.out.println(days[i] + " → " +
                    (week[i] == null ? "FREE" : week[i].title));
        }
    }
}