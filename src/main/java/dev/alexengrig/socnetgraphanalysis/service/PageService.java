package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.converter.ClusteringConditionModel2ClusteringRequestConverter;
import dev.alexengrig.socnetgraphanalysis.converter.ClusteringResponse2ClusteringModelConverter;
import dev.alexengrig.socnetgraphanalysis.converter.VkUserProperty2OptionConverter;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringRequest;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringResponse;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import dev.alexengrig.socnetgraphanalysis.model.ClusteringConditionModel;
import dev.alexengrig.socnetgraphanalysis.model.ClusteringModel;
import dev.alexengrig.socnetgraphanalysis.model.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageService {

    private final VkUserProperty2OptionConverter vkUserProperty2OptionConverter;
    private final ClusteringService clusteringService;
    private final ClusteringConditionModel2ClusteringRequestConverter clusteringConditionModel2ClusteringRequestConverter;
    private final ClusteringResponse2ClusteringModelConverter clusteringResponse2ClusteringModelConverter;

    public List<Option> getPropertyOptions() {
        return Arrays.stream(VkUserProperty.values())
                .map(vkUserProperty2OptionConverter::convert)
                .collect(Collectors.toList());
    }

    public ClusteringModel clustering(ClusteringConditionModel conditionModel) {
        ClusteringRequest clusteringRequest = clusteringConditionModel2ClusteringRequestConverter.convert(conditionModel);
        ClusteringResponse clusteringResponse = clusteringService.kMeans(clusteringRequest);
        return clusteringResponse2ClusteringModelConverter.convert(clusteringResponse);
    }
}
