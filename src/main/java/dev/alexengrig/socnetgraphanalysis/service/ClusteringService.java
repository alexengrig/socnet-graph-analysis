package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.clustering.Distance;
import dev.alexengrig.socnetgraphanalysis.clustering.EuclideanDistance;
import dev.alexengrig.socnetgraphanalysis.clustering.KMeansAlgorithm;
import dev.alexengrig.socnetgraphanalysis.converter.ClusterRecordConverter;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterCentroid;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringRequest;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringResponse;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.factory.KMeansAlgorithmFactory;
import dev.alexengrig.socnetgraphanalysis.randomizer.VkUserRandomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClusteringService {

    private final VkUserService vkUserService;
    private final ClusterRecordConverter clusterRecordConverter;
    private final KMeansAlgorithmFactory algorithmFactory;
    private final VkUserRandomizer vkUserRandomizer;

    public ClusteringResponse kMeans(ClusteringRequest request) {
        Set<ClusterRecord> records = getClusterRecords(request);
        int numberOfClusters = request.getNumberOfClusters();
        EuclideanDistance distance = new EuclideanDistance();
        KMeansAlgorithm kMeansAlgorithm = algorithmFactory.createAlgorithm(numberOfClusters, distance);
        Map<ClusterCentroid, Set<ClusterRecord>> clusters = kMeansAlgorithm.apply(records);
        return ClusteringResponse.builder()
                .clusters(clusters)
                .build();
    }

    private Set<ClusterRecord> getClusterRecords(ClusteringRequest request) {
        List<VkUser> users = request.isTest() ? getTestVkUsers(request) : getVkUsers(request);
        request.setVkUsers(users);
        return users.stream()
                .map(u -> clusterRecordConverter.convert(u, request.getProperties()))
                .collect(Collectors.toSet());
    }

    private List<VkUser> getTestVkUsers(ClusteringRequest request) {
        Integer count = request.getCount();
        if (count == null) {
            count = 250;
        }
        return vkUserRandomizer.randomVkUsers(count);
    }

    private List<VkUser> getVkUsers(ClusteringRequest request) {
        String vkUserId = request.getVkUserId();
        VkUser user = vkUserService.getUserById(request.getCode(), vkUserId, request.getProperties()).orElseThrow();
        List<VkUser> friends = vkUserService.getUserFriendsById(request.getCode(), user.getId(), request.getProperties());
        List<VkUser> users = new ArrayList<>(1 + friends.size());
        users.add(user);
        users.addAll(friends);
        return users;
    }

    public List<Double> elbow(ClusteringRequest request) {
        Set<ClusterRecord> records = getClusterRecords(request);
        EuclideanDistance distance = new EuclideanDistance();
        List<Double> sumOfSquaredErrors = new ArrayList<>();
        for (int numberOfClusters = 2; numberOfClusters <= 16; numberOfClusters++) {
            KMeansAlgorithm kMeansAlgorithm = algorithmFactory.createAlgorithm(numberOfClusters, distance);
            Map<ClusterCentroid, Set<ClusterRecord>> clusters = kMeansAlgorithm.apply(records);
            double sse = sse(clusters, distance);
            sumOfSquaredErrors.add(sse);
        }
        return sumOfSquaredErrors;
    }

    public double sse(Map<ClusterCentroid, Set<ClusterRecord>> clusters, Distance distance) {
        double sum = 0;
        for (Map.Entry<ClusterCentroid, Set<ClusterRecord>> entry : clusters.entrySet()) {
            ClusterCentroid centroid = entry.getKey();
            for (ClusterRecord record : entry.getValue()) {
                double d = distance.calculate(centroid.getCoordinates(), record.getParameters());
                sum += Math.pow(d, 2);
            }
        }
        return sum;
    }
}
