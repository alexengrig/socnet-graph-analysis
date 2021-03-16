package dev.alexengrig.socnetgraphanalysis.clustering;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Record {

    private final String label;
    private final Parameters parameters;
}
