package dev.alexengrig.socnetgraphanalysis.clustering;

import dev.alexengrig.socnetgraphanalysis.domain.ClusterCentroid;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecordParameters;
import lombok.Builder;
import lombok.Value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class KMeansAlgorithm implements ClusteringAlgorithm {

    Distance distance;
    int numberOfClusters;
    @Builder.Default
    int maxIterations = Integer.MAX_VALUE;
    @Builder.Default
    Random random = new Random();

    @Override
    public Map<ClusterCentroid, Set<ClusterRecord>> apply(Set<ClusterRecord> records) {
        Set<ClusterCentroid> centroids = randomCentroids(records);
        Map<ClusterCentroid, Set<ClusterRecord>> clusters = new HashMap<>();
        Map<ClusterCentroid, Set<ClusterRecord>> lastState = new HashMap<>();

        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            for (ClusterRecord record : records) {
                ClusterCentroid centroid = nearestCentroid(centroids, record, distance);
                assignToCluster(clusters, centroid, record);
            }

            boolean shouldTerminate = isLastIteration || clusters.equals(lastState);
            lastState = clusters;
            if (shouldTerminate) {
                break;
            }

            centroids = relocateCentroids(clusters);
            clusters = new HashMap<>();
        }

        return lastState;
    }

    private Set<ClusterCentroid> randomCentroids(Set<ClusterRecord> records) {
        Map<String, Double> maxByFeature = new HashMap<>();
        Map<String, Double> minByFeature = new HashMap<>();

        records.forEach(record -> record.getParameters().forEach((key, value) -> {
            maxByFeature.compute(key, (ignore, max) -> max == null || value > max ? value : max);
            minByFeature.compute(key, (ignore, min) -> min == null || value < min ? value : min);
        }));

        Set<String> attributes = records.stream()
                .flatMap(record -> record.getParameters().names().stream())
                .collect(Collectors.toSet());

        Set<ClusterCentroid> centroids = new HashSet<>();

        for (int i = 0; i < numberOfClusters; i++) {
            Map<String, Double> coordinates = new HashMap<>();
            for (String attribute : attributes) {
                double max = maxByFeature.get(attribute);
                double min = minByFeature.get(attribute);
                double value = random.nextDouble() * (max - min) + min;
                coordinates.put(attribute, value);
            }
            centroids.add(ClusterCentroid.builder()
                    .coordinates(new ClusterRecordParameters(coordinates))
                    .build());
        }

        return centroids;
    }

    private ClusterCentroid nearestCentroid(Set<ClusterCentroid> centroids, ClusterRecord record, Distance distance) {
        double minimumDistance = Double.MAX_VALUE;
        ClusterCentroid nearest = null;

        for (ClusterCentroid centroid : centroids) {
            double currentDistance = distance.calculate(record.getParameters(), centroid.getCoordinates());
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        return nearest;
    }

    private void assignToCluster(Map<ClusterCentroid, Set<ClusterRecord>> clusters, ClusterCentroid centroid, ClusterRecord record) {
        clusters.compute(centroid, (ignore, set) -> {
            if (set == null) {
                set = new HashSet<>();
            }
            set.add(record);
            return set;
        });
    }

    private Set<ClusterCentroid> relocateCentroids(Map<ClusterCentroid, Set<ClusterRecord>> clusters) {
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(Collectors.toSet());
    }

    private ClusterCentroid average(ClusterCentroid centroid, Set<ClusterRecord> records) {
        if (records == null || records.isEmpty()) {
            return centroid;
        }

        ClusterRecordParameters average = centroid.getCoordinates();
        records.stream().flatMap(r -> r.getParameters().names().stream()).forEach(name -> average.add(name, 0.0));

        for (ClusterRecord record : records) {
            record.getParameters().forEach((name, value) -> average.update(name, currentValue -> value + currentValue));
        }

        average.forEach((name, value) -> average.add(name, value / records.size()));

        return ClusterCentroid.builder()
                .coordinates(average)
                .build();
    }
}
