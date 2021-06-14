package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusterCentroid;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecordParameters;
import dev.alexengrig.socnetgraphanalysis.domain.ClusteringRequest;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.model.Node;
import dev.alexengrig.socnetgraphanalysis.model.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClusterConverter {

    private final VkUserPropertyValueConverter vkUserPropertyValueConverter;

    public Parent convert(ClusteringRequest request, Map<ClusterCentroid, Set<ClusterRecord>> source) {
        Map<Integer, String> countryMap = request.getVkUsers().stream()
                .collect(Collectors.toMap(VkUser::getCountry, VkUser::getCountryName, (l, r) -> l));
        Map<Integer, String> cityMap = request.getVkUsers().stream()
                .collect(Collectors.toMap(VkUser::getCity, VkUser::getCityName, (l, r) -> l));
        List<Node> target = new ArrayList<>(source.size());
        int i = 1;
        for (Map.Entry<ClusterCentroid, Set<ClusterRecord>> entry : source.entrySet()) {
            List<Node> children = entry.getValue().stream()
                    .map(record -> new Node(record.getLabel(), getDetails(record.getParameters(), countryMap, cityMap)))
                    .collect(Collectors.toList());
            String detail = "Кол-во пользователей: " + children.size() + ", "
                    + getDetails(entry.getKey().getCoordinates(), countryMap, cityMap);
            Parent parent = new Parent("Кластер №" + i++, detail, children);
            target.add(parent);
        }
        return new Parent("Пользователи", "Кол-во кластеров: " + target.size(), target);
    }

    private String getDetails(ClusterRecordParameters parameters,
                              Map<Integer, String> countryMap, Map<Integer, String> cityMap) {
        return parameters.getMap().entrySet().stream()
                .map(e -> e.getKey() + ": "
                        + vkUserPropertyValueConverter.convert(e.getKey(), e.getValue(), countryMap, cityMap))
                .collect(Collectors.joining(", "));
    }
}
