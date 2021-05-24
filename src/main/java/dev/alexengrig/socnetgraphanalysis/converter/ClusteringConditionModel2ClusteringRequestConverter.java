package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusteringRequest;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import dev.alexengrig.socnetgraphanalysis.model.ClusteringConditionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class ClusteringConditionModel2ClusteringRequestConverter implements Converter<ClusteringConditionModel, ClusteringRequest> {

    private final VkUserPropertyConverter vkUserPropertyConverter;

    @NonNull
    @Override
    public ClusteringRequest convert(ClusteringConditionModel source) {
        return ClusteringRequest.builder()
                .vkUserId(source.getVkUserId())
                .numberOfClusters(source.getNumberOfClusters())
                .properties(getProperties(source))
                .code(source.getCode())
                .test("test".equals(source.getTest()))
                .count(source.getCount())
                .build();
    }

    private Set<VkUserProperty> getProperties(ClusteringConditionModel source) {
        if (isNull(source.getPropertyOptions())) {
            return Collections.emptySet();
        }
        return source.getPropertyOptions().stream()
                .map(vkUserPropertyConverter::convert)
                .collect(Collectors.toSet());
    }
}
