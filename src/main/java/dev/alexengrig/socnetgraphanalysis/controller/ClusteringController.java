package dev.alexengrig.socnetgraphanalysis.controller;


import dev.alexengrig.socnetgraphanalysis.clustering.Centroid;
import dev.alexengrig.socnetgraphanalysis.clustering.Record;
import dev.alexengrig.socnetgraphanalysis.service.ClusteringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clustering")
@RequiredArgsConstructor
public class ClusteringController {

    private final ClusteringService clusteringService;

    @GetMapping("/{vkUserId}")
    public Map<Centroid, List<Record>> getUserById(@PathVariable String vkUserId) {
        return clusteringService.kMeans(vkUserId);
    }
}
