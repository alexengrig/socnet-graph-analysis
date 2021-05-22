package dev.alexengrig.socnetgraphanalysis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Option {

    private String id;
    private String label;
}
