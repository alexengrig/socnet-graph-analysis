package dev.alexengrig.socnetgraphanalysis.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Option {

    String id;
    String label;
}
