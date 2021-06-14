package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusteringRequest;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringResponse;
import dev.alexengrig.socnetgraphanalysis.model.ClusteringModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClusteringResponse2ClusteringModelConverter {

    private final VkUserProperty2OptionConverter vkUserProperty2OptionConverter;
    private final VkUserTableConverter vkUserTableConverter;
    private final ClusterConverter clusterConverter;

    public ClusteringModel convert(ClusteringRequest request, ClusteringResponse response) {
        return ClusteringModel.builder()
                .vkUserId(request.getVkUserId())
                .numberOfClusters(request.getNumberOfClusters())
                .numberOfUsers(request.getVkUsers().size())
                .options(request.getProperties().stream()
                        .map(vkUserProperty2OptionConverter::convert)
                        .collect(Collectors.toList()))
                .table(vkUserTableConverter.convert(request))
                .cluster(clusterConverter.convert(request, response.getClusters()))
                .build();
    }
}
