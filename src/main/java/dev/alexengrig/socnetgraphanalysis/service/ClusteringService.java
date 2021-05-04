package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.clustering.EuclideanDistance;
import dev.alexengrig.socnetgraphanalysis.clustering.KMeansAlgorithm;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterCentroid;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.model.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClusteringService {

    private final ConversionService conversionService;
    private final VkUserService vkUserService;

    public Parent kMeans(String vkUserId) {
        VkUser user = vkUserService.getUserById(vkUserId).orElseThrow();
        List<VkUser> friends = vkUserService.getUserFriendsById(user.getId());
        List<VkUser> users = new ArrayList<>(1 + friends.size());
        users.add(user);
        users.addAll(friends);
        Set<ClusterRecord> records = users.stream()
                .map(u -> conversionService.convert(u, ClusterRecord.class))
                .collect(Collectors.toSet());
        KMeansAlgorithm kMeansAlgorithm = KMeansAlgorithm.builder()
                .distance(new EuclideanDistance())
                .numberOfClusters(5)
                .build();
        Map<ClusterCentroid, Set<ClusterRecord>> clusters = kMeansAlgorithm.apply(records);
        return conversionService.convert(clusters, Parent.class);
    }
}
