package dev.alexengrig.socnetgraphanalysis.converter;

import com.vk.api.sdk.objects.users.UserCounters;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Component
public class VkUserConverter implements Converter<GetResponse, VkUser> {

    private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");

    @NonNull
    @Override
    public VkUser convert(GetResponse source) {
        return VkUser.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .birthday(source.getBdate())
                .age(getAge(source.getBdate()))
                .accessed(hasAccess(source))
                .audiosCount(getCounter(source.getCounters(), UserCounters::getAudios))
                .build();
    }

    private Integer getAge(String birthday) {
        if (birthday == null || birthday.length() < 8) {
            return 0;
        }
        LocalDate date = LocalDate.parse(birthday, BIRTHDAY_FORMATTER);
        return LocalDate.now().getYear() - date.getYear();
    }

    private boolean hasAccess(GetResponse source) {
        return !"deleted".equals(source.getDeactivated()) && !"banned".equals(source.getDeactivated())
                && (source.getIsClosed() == null || !source.getIsClosed()
                || (source.getCanAccessClosed() != null && !source.getCanAccessClosed()));
    }

    private Integer getCounter(UserCounters counters, Function<UserCounters, Integer> getter) {
        Integer result;
        if (counters == null || (result = getter.apply(counters)) == null) {
            return 0;
        }
        return result;
    }
}
