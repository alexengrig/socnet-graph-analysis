package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class VkUserPropertyConverter implements Converter<String, VkUserProperty> {

    @Override
    public VkUserProperty convert(String source) {
        return Arrays.stream(VkUserProperty.values())
                .filter(p -> p.getId().equals(source))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Vk user property not found by id: " + source));
    }
}
