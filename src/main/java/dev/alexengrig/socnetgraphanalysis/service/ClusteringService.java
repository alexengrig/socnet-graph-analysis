package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.clustering.KMeansAlgorithm;
import dev.alexengrig.socnetgraphanalysis.converter.ClusterRecordConverter;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterCentroid;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringRequest;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringResponse;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.factory.KMeansAlgorithmFactory;
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

    public ClusteringResponse kMeans(ClusteringRequest request) {
        String vkUserId = request.getVkUserId();
        VkUser user = vkUserService.getUserById(request.getCode(), vkUserId).orElseThrow();
        List<VkUser> friends = vkUserService.getUserFriendsById(request.getCode(), user.getId());
        List<VkUser> users = new ArrayList<>(1 + friends.size());
        users.add(user);
        users.addAll(friends);
        Set<ClusterRecord> records = users.stream()
                .map(u -> clusterRecordConverter.convert(u, request.getProperties()))
                .collect(Collectors.toSet());
        int numberOfClusters = request.getNumberOfClusters();
        KMeansAlgorithm kMeansAlgorithm = algorithmFactory.createAlgorithm(numberOfClusters);
        Map<ClusterCentroid, Set<ClusterRecord>> clusters = kMeansAlgorithm.apply(records);
        return ClusteringResponse.builder()
                .clusters(clusters)
                .build();
    }
}
