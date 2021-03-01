package dev.alexengrig.socnetgraphanalysis.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
@Builder
public class Person {
    @Id
    private Integer id;
}
