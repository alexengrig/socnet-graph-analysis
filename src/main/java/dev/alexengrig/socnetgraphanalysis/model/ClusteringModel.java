package dev.alexengrig.socnetgraphanalysis.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClusteringModel {

    private Parent cluster;
}
