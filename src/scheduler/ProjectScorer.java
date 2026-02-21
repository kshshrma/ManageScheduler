package scheduler;

import model.Project;

public class ProjectScorer {

    public static double score(Project p, double futureAvg) {

        double urgency = 1.0 / p.deadline;
        double risk = p.deadline <= 5 ? 1 : 0;
        double penalty = (p.deadline > 5 && p.revenue < futureAvg) ? 1 : 0;

        return (0.6 * p.revenue)
                + (0.3 * urgency)
                + (0.1 * risk)
                - (0.2 * penalty);
    }
}