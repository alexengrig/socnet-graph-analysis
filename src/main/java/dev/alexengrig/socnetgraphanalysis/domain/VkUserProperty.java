package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VkUserProperty {

    AGE("age", "Age"),
    ACCESSED("accessed", "Accessed");

    private final String id;
    private final String label;
}
