package dev.alexengrig.socnetgraphanalysis.converter;

import com.vk.api.sdk.objects.users.responses.GetResponse;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class VkUserConverter implements Converter<GetResponse, VkUser> {

    @NonNull
    @Override
    public VkUser convert(GetResponse source) {
        return VkUser.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .birthday(source.getBdate())
                .build();
    }
}
