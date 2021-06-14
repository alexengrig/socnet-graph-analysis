package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.ClusteringRequest;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import dev.alexengrig.socnetgraphanalysis.model.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VkUserTableConverter implements Converter<ClusteringRequest, Table> {

    private final VkUserPropertyValueConverter vkUserPropertyValueConverter;

    @Override
    public Table convert(ClusteringRequest source) {
        List<VkUser> users = source.getVkUsers();
        List<VkUserProperty> properties = source.getProperties().stream()
                .sorted(Comparator.comparing(Enum::ordinal))
                .collect(Collectors.toList());
        return Table.builder()
                .headers(createHeaders(properties))
                .rows(createRows(users, properties))
                .build();
    }

    private List<String> createHeaders(List<VkUserProperty> properties) {
        List<String> target = new ArrayList<>(3 + properties.size());
        target.add("Идентификатор");
        target.add("Фамилия");
        target.add("Имя");
        properties.stream().map(VkUserProperty::getLabel).forEach(target::add);
        return target;
    }

    private List<List<String>> createRows(List<VkUser> users, List<VkUserProperty> properties) {
        return users.stream()
                .map(getVkUserStringValues(properties))
                .collect(Collectors.toList());
    }

    private Function<VkUser, List<String>> getVkUserStringValues(List<VkUserProperty> properties) {
        return user -> {
            List<String> target = new ArrayList<>(3 + properties.size());
            target.add(String.valueOf(user.getId()));
            target.add(user.getLastName());
            target.add(user.getFirstName());
            properties.stream().map(getVkUserStringValue(user)).forEach(target::add);
            return target;
        };
    }

    private Function<VkUserProperty, String> getVkUserStringValue(VkUser user) {
        return property -> vkUserPropertyValueConverter.convert(property, user);
    }
}
