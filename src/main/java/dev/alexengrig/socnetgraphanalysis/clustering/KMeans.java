package dev.alexengrig.socnetgraphanalysis.clustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class KMeans {

    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        List<Record> target = Arrays.asList(Record.builder()
                        .label("Record #1")
                        .parameters(new Parameters(Map.of("Sex", 1d, "Age", 20d)))
                        .build(),
                Record.builder()
                        .label("Record #2")
                        .parameters(new Parameters(Map.of("Sex", 0d, "Age", 40d)))
                        .build(),
                Record.builder()
                        .label("Record #3")
                        .parameters(new Parameters(Map.of("Sex", 1d, "Age", 30d)))
                        .build(),
                Record.builder()
                        .label("Record #4")
                        .parameters(new Parameters(Map.of("Sex", 1d, "Age", 15d)))
                        .build(),
                Record.builder()
                        .label("Record #5")
                        .parameters(new Parameters(Map.of("Sex", 0d, "Age", 30d)))
                        .build());
        Map<Centroid, List<Record>> clusters = fit(target, 2, new EuclideanDistance(), 10000);
        clusters.forEach((centroid, records) -> {
            System.out.println("--Cluster--");
            System.out.println(centroid);
            System.out.println(records.stream().map(Record::getLabel).collect(Collectors.joining(", ")));
        });
    }

    public static Map<Centroid, List<Record>> fit(List<Record> records, int k, Distance distance, int maxIterations) {
        List<Centroid> centroids = randomCentroids(records, k);
        Map<Centroid, List<Record>> clusters = new HashMap<>();
        Map<Centroid, List<Record>> lastState = new HashMap<>();

        for (int i = 0; i < maxIterations; i++) {
            boolean isLastIteration = i == maxIterations - 1;

            for (Record record : records) {
                Centroid centroid = nearestCentroid(centroids, record, distance);
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

    private static List<Centroid> randomCentroids(List<Record> records, int k) {
        Map<String, Double> maxByFeature = new HashMap<>();
        Map<String, Double> minByFeature = new HashMap<>();

        records.forEach(record -> record.getParameters().forEach((key, value) -> {
            maxByFeature.compute(key, (ignore, max) -> max == null || value > max ? value : max);
            minByFeature.compute(key, (ignore, min) -> min == null || value < min ? value : min);
        }));

        Set<String> attributes = records.stream()
                .flatMap(record -> record.getParameters().names().stream())
                .collect(Collectors.toSet());

        List<Centroid> centroids = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            Map<String, Double> coordinates = new HashMap<>();
            for (String attribute : attributes) {
                double max = maxByFeature.get(attribute);
                double min = minByFeature.get(attribute);
                coordinates.put(attribute, RANDOM.nextDouble() * (max - min) + min);
            }
            centroids.add(new Centroid(new Parameters(coordinates)));
        }

        return centroids;
    }

    private static Centroid nearestCentroid(List<Centroid> centroids, Record record, Distance distance) {
        double minimumDistance = Double.MAX_VALUE;
        Centroid nearest = null;

        for (Centroid centroid : centroids) {
            double currentDistance = distance.calculate(record.getParameters(), centroid.getCoordinates());
            if (currentDistance < minimumDistance) {
                minimumDistance = currentDistance;
                nearest = centroid;
            }
        }

        return nearest;
    }

    private static void assignToCluster(Map<Centroid, List<Record>> clusters, Centroid centroid, Record record) {
        clusters.compute(centroid, (ignore, list) -> {
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(record);
            return list;
        });
    }

    private static List<Centroid> relocateCentroids(Map<Centroid, List<Record>> clusters) {
        return clusters.entrySet().stream().map(e -> average(e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    private static Centroid average(Centroid centroid, List<Record> records) {
        if (records == null || records.isEmpty()) {
            return centroid;
        }

        Parameters average = centroid.getCoordinates();
        records.stream().flatMap(r -> r.getParameters().names().stream()).forEach(name -> average.add(name, 0.0));

        for (Record record : records) {
            record.getParameters().forEach((name, value) -> average.update(name, currentValue -> value + currentValue));
        }

        average.forEach((name, value) -> average.add(name, value / records.size()));

        return new Centroid(average);
    }
}
