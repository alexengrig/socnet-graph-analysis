package dev.alexengrig.socnetgraphanalysis.converter;

import com.vk.api.sdk.objects.users.UserCounters;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class VkUserConverter implements Converter<GetResponse, VkUser> {

    @NonNull
    @Override
    public VkUser convert(GetResponse source) {
        VkUser.VkUserBuilder builder = VkUser.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .birthday(source.getBdate());
        if (hasNoAccess(source)) {
            return builder.build();
        }
        return builder
                .accessed(true)
                .audiosCount(getCounter(source.getCounters(), UserCounters::getAudios))
                .build();
    }

    private boolean hasNoAccess(GetResponse source) {
        return "deleted".equals(source.getDeactivated()) || "banned".equals(source.getDeactivated())
                || (source.getIsClosed() != null && source.getIsClosed()
                && source.getCanAccessClosed() != null && source.getCanAccessClosed());
    }

    private Integer getCounter(UserCounters counters, Function<UserCounters, Integer> getter) {
        Integer result;
        if (counters == null || (result = getter.apply(counters)) == null) {
            return 0;
        }
        return result;
    }
}
