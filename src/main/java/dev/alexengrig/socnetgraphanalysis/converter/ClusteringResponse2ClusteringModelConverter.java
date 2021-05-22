package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusteringResponse;
import dev.alexengrig.socnetgraphanalysis.model.ClusteringModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClusteringResponse2ClusteringModelConverter implements Converter<ClusteringResponse, ClusteringModel> {

    private final ClusterConverter clusterConverter;

    @Override
    public ClusteringModel convert(ClusteringResponse source) {
        return ClusteringModel.builder()
                .cluster(clusterConverter.convert(source.getClusters()))
                .build();
    }
}
