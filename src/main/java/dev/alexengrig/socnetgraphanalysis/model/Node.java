package dev.alexengrig.socnetgraphanalysis.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Node {

    private final String label;
    private final String details;
}
