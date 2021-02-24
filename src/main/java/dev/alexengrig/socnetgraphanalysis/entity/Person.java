package dev.alexengrig.socnetgraphanalysis.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
public class Person {
    @Id
    private Integer id;
}
