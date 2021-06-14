package dev.alexengrig.socnetgraphanalysis.factory;

import dev.alexengrig.socnetgraphanalysis.clustering.EuclideanDistance;
import dev.alexengrig.socnetgraphanalysis.clustering.KMeansAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class KMeansAlgorithmFactory {

    public KMeansAlgorithm createAlgorithm(int numberOfClusters, EuclideanDistance distance) {
        return KMeansAlgorithm.builder()
                .distance(distance)
                .numberOfClusters(numberOfClusters)
                .build();
    }
}
