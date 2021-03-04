package dev.alexengrig.socnetgraphanalysis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Man {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;
    private String name;

    private Integer audiosCount;
}
