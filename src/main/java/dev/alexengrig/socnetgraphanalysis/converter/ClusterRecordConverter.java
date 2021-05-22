package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecordParameters;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ClusterRecordConverter {

    @NonNull
    public ClusterRecord convert(@NonNull VkUser source, Set<VkUserProperty> properties) {
        return ClusterRecord.builder()
                .id(source.getId())
                .label(getLabel(source))
                .parameters(getParameters(source, properties))
                .build();
    }

    private String getLabel(VkUser source) {
        return String.join(" ", source.getFirstName(), source.getLastName());
    }

    private ClusterRecordParameters getParameters(VkUser source, Set<VkUserProperty> properties) {
        Map<String, Double> map = properties.stream().collect(Collectors.toMap(VkUserProperty::getLabel, p -> p.get(source)));
        return new ClusterRecordParameters(map);
    }
}
