package dev.alexengrig.socnetgraphanalysis.clustering;

import dev.alexengrig.socnetgraphanalysis.domain.ClusterCentroid;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;

import java.util.Map;
import java.util.Set;

@FunctionalInterface
public interface ClusteringAlgorithm {

    Map<ClusterCentroid, Set<ClusterRecord>> apply(Set<ClusterRecord> records);
}
