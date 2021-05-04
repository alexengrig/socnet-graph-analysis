package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusterCentroid;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.model.Node;
import dev.alexengrig.socnetgraphanalysis.model.Parent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClusterConverter implements Converter<Map<ClusterCentroid, Set<ClusterRecord>>, Parent> {

    @Override
    public Parent convert(Map<ClusterCentroid, Set<ClusterRecord>> source) {
        List<Node> target = new ArrayList<>(source.size());
        int i = 1;
        for (Map.Entry<ClusterCentroid, Set<ClusterRecord>> entry : source.entrySet()) {
            String name = "Cluster " + i++;
            List<Node> children = entry.getValue().stream()
                    .map(record -> new Node(record.getLabel()))
                    .collect(Collectors.toList());
            Parent parent = new Parent(name, children);
            target.add(parent);
        }
        return new Parent("Users", target);
    }
}
