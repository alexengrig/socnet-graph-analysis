package dev.alexengrig.socnetgraphanalysis.clustering;

public interface Distance {

    double calculate(Parameters left, Parameters right);
}
