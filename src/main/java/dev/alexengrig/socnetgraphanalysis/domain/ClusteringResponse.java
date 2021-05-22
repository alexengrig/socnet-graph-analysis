package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ClusteringResponse {

    private Map<ClusterCentroid, Set<ClusterRecord>> clusters;
}
