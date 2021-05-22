package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.converter.VkUserProperty2OptionConverter;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
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

    public List<Option> getPropertyOptions() {
        return Arrays.stream(VkUserProperty.values())
                .map(vkUserProperty2OptionConverter::convert)
                .collect(Collectors.toList());
    }
}
