package dev.alexengrig.socnetgraphanalysis.clustering;

public class EuclideanDistance implements Distance {

    @Override
    public double calculate(Parameters left, Parameters right) {
        if (!left.like(right)) {
            throw new IllegalArgumentException("Different parameters: " + left + " and " + right);
        }
        double sum = 0;
        for (String key : left.names()) {
            Double leftValue = left.get(key);
            Double rightValue = right.get(key);
            sum += Math.pow(leftValue - rightValue, 2);
        }
        return Math.sqrt(sum);
    }
}
