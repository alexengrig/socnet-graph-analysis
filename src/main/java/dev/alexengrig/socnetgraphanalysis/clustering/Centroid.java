package dev.alexengrig.socnetgraphanalysis.clustering;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Centroid {

    private final Parameters coordinates;
}
