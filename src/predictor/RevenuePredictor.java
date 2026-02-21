package predictor;

public class RevenuePredictor {

    public static double predict(int[] weeks) {
        double[] w = {0.5, 0.3, 0.2};
        double sum = 0;
        for (int i = 0; i < w.length; i++)
            sum += weeks[i] * w[i];
        return sum;
    }
}