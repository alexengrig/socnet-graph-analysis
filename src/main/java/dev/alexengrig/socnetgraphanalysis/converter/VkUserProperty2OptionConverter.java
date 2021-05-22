package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import dev.alexengrig.socnetgraphanalysis.model.Option;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VkUserProperty2OptionConverter implements Converter<VkUserProperty, Option> {

    @Override
    public Option convert(VkUserProperty source) {
        return Option.builder()
                .id(source.getId())
                .label(source.getLabel())
                .build();
    }
}
