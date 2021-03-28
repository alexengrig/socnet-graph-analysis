package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.clustering.Centroid;
import dev.alexengrig.socnetgraphanalysis.clustering.EuclideanDistance;
import dev.alexengrig.socnetgraphanalysis.clustering.KMeans;
import dev.alexengrig.socnetgraphanalysis.clustering.Record;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.model.Parent;
import dev.alexengrig.socnetgraphanalysis.service.vk.VkUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        List<Record> records = users.stream()
                .map(u -> conversionService.convert(u, Record.class))
                .collect(Collectors.toList());
        Map<Centroid, List<Record>> clusters = KMeans.fit(records, 5, new EuclideanDistance(), 100);
        return conversionService.convert(clusters, Parent.class);
    }
}
