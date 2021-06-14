package dev.alexengrig.socnetgraphanalysis.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Table {
    List<String> headers;
    List<List<String>> rows;
}
