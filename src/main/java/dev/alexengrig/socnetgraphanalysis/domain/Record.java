package dev.alexengrig.socnetgraphanalysis.domain;

import dev.alexengrig.socnetgraphanalysis.clustering.Parameters;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Record {

    @EqualsAndHashCode.Include
    Integer id;
    String label;
    Parameters parameters;
}
