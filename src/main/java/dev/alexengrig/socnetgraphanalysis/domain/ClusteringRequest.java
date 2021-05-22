package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ClusteringRequest {

    String vkUserId;
    Integer numberOfClusters;
    Set<VkUserProperty> properties;
}
