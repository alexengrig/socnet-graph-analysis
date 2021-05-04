package dev.alexengrig.socnetgraphanalysis.clustering;

import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecordParameters;

public interface Distance {

    double calculate(ClusterRecordParameters left, ClusterRecordParameters right);
}
