package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClusterCentroid {

    ClusterRecordParameters coordinates;
}
