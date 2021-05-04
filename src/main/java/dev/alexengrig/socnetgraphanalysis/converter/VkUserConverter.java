package dev.alexengrig.socnetgraphanalysis.converter;

import com.vk.api.sdk.objects.base.City;
import com.vk.api.sdk.objects.base.Country;
import com.vk.api.sdk.objects.base.Sex;
import com.vk.api.sdk.objects.friends.FriendStatusStatus;
import com.vk.api.sdk.objects.users.Personal;
import com.vk.api.sdk.objects.users.UserCounters;
import com.vk.api.sdk.objects.users.UserRelation;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@Component
public class VkUserConverter implements Converter<GetResponse, VkUser> {

    private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");

    @NonNull
    @Override
    public VkUser convert(@NonNull GetResponse source) {
        return VkUser.builder()
                .id(source.getId())
                .firstName(getString(source.getFirstName()))
                .lastName(getString(source.getLastName()))
                .birthday(source.getBdate())
                .age(getAge(source.getBdate()))
                .accessed(hasAccess(source))
                .sex(getInteger(source.getSex(), Sex::ordinal))
                .city(getInteger(source.getCity(), City::getId))
                .country(getInteger(source.getCountry(), Country::getId))
                .friendStatus(getInteger(source.getFriendStatus(), FriendStatusStatus::ordinal))
                .relation(getInteger(source.getRelation(), UserRelation::ordinal))
                .political(getInteger(source.getPersonal(), Personal::getPolitical))
                .religion(getInteger(source.getPersonal(), Personal::getReligionId))
                .lifeMain(getInteger(source.getPersonal(), Personal::getLifeMain))
                .peopleMain(getInteger(source.getPersonal(), Personal::getPeopleMain))
                .smoking(getInteger(source.getPersonal(), Personal::getSmoking))
                .alcohol(getInteger(source.getPersonal(), Personal::getAlcohol))
                .commonFriendsCount(getInteger(source.getCommonCount()))
                .friendsCount(getInteger(source.getCounters(), UserCounters::getFriends))
                .followersCount(getInteger(source.getCounters(), UserCounters::getFollowers))
                .groupsCount(getInteger(source.getCounters(), UserCounters::getGroups))
                .audiosCount(getInteger(source.getCounters(), UserCounters::getAudios))
                .videosCount(getInteger(source.getCounters(), UserCounters::getVideos))
                .photosCount(getInteger(source.getCounters(), UserCounters::getPhotos))
                .albumsCount(getInteger(source.getCounters(), UserCounters::getAlbums))
                .notesCount(getInteger(source.getCounters(), UserCounters::getNotes))
                .pagesCount(getInteger(source.getCounters(), UserCounters::getPages))
                .build();
    }

    private Integer getAge(String birthday) {
        if (birthday == null || birthday.length() < 8) {
            return 0;
        }
        LocalDate date = LocalDate.parse(birthday, BIRTHDAY_FORMATTER);
        return (int) ChronoUnit.YEARS.between(date, LocalDate.now());
    }

    private boolean hasAccess(GetResponse source) {
        boolean isDeleted = "deleted".equals(source.getDeactivated());
        boolean isBanned = "banned".equals(source.getDeactivated());
        if (isDeleted || isBanned) {
            return false;
        }
        boolean isClosed = source.getIsClosed() == null || !source.getIsClosed();
        boolean canAccessClosed = source.getCanAccessClosed() != null && !source.getCanAccessClosed();
        return isClosed || canAccessClosed;
    }

    private String getString(String value) {
        return value == null ? "" : value;
    }

    private <T> Integer getInteger(T object, Function<T, Integer> getter) {
        if (object == null) {
            return 0;
        }
        Integer value = getter.apply(object);
        return getInteger(value);
    }

    private Integer getInteger(Integer value) {
        return value == null ? 0 : value;
    }
}
