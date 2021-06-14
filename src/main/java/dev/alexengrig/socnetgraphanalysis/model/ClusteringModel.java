package dev.alexengrig.socnetgraphanalysis.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClusteringModel {

    private String vkUserId;
    private Integer numberOfClusters;
    private List<Option> options;

    private Table table;
    private Parent cluster;
}
