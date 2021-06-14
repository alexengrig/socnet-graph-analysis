package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ClusteringRequest {

    String vkUserId;
    Integer numberOfClusters;
    Set<VkUserProperty> properties;
    String code;
    boolean test;
    Integer count;
    List<VkUser> vkUsers;
}
