package dev.alexengrig.socnetgraphanalysis.model;

import lombok.Data;

import java.util.List;

@Data
public class ClusteringConditionModel {

    private String vkUserId;
    private Integer numberOfClusters;
    private List<String> propertyOptions;
}
