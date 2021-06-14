package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public enum VkUserProperty {

    ACCESSED("accessed", "Доступ к аккаунту", user -> user.isAccessed() ? 1d : 0d),
    AGE("age", "Возраст", user -> user.getAge().doubleValue()),
    SEX("sex", "Пол", user -> user.getSex().doubleValue()),
    CITY("city", "Город", user -> user.getCity().doubleValue()),
    COUNTRY("country", "Страна", user -> user.getCountry().doubleValue()),
    FRIEND_STATUS("friend_status", "Статус дружбы", user -> user.getFriendStatus().doubleValue()),
    RELATION("relation", "Семейное положение", user -> user.getRelation().doubleValue()),
    POLITICAL("political", "Полит. предпочтения", user -> user.getPolitical().doubleValue()),
    RELIGION("religion", "Мировоззрение", user -> user.getReligion().doubleValue()),
    LIFE_MAIN("life_main", "Главное в жизни", user -> user.getLifeMain().doubleValue()),
    PEOPLE_MAIN("people_main", "Главное в людях", user -> user.getPeopleMain().doubleValue()),
    SMOKING("smoking", "Отношение к курению", user -> user.getSmoking().doubleValue()),
    ALCOHOL("alcohol", "Отношение к алкоголю", user -> user.getAlcohol().doubleValue()),
    COMMON_FRIENDS_COUNT("common_friends_count", "Кол-во общих друзей", user -> user.getCommonFriendsCount().doubleValue()),
    FRIENDS_COUNT("friends_count", "Кол-во друзей", user -> user.getFriendsCount().doubleValue()),
    FOLLOWERS_COUNT("followers_count", "Кол-во подписчиков", user -> user.getFollowersCount().doubleValue()),
    AUDIOS_COUNT("audios_count", "Кол-во аудиозаписей", user -> user.getAudiosCount().doubleValue()),
    VIDEOS_COUNT("videos_count", "Кол-во видеозаписей", user -> user.getVideosCount().doubleValue()),
    PHOTOS_COUNT("photos_count", "Кол-во фотографий", user -> user.getPhotosCount().doubleValue()),
    GROUPS_COUNT("groups_count", "Кол-во сообществ", user -> user.getGroupsCount().doubleValue()),
    ALBUMS_COUNT("albums_count", "Кол-во фотоальбомов", user -> user.getAlbumsCount().doubleValue()),
    NOTES_COUNT("notes_count", "Кол-во заметок", user -> user.getNotesCount().doubleValue()),
    PAGES_COUNT("pages_count", "Кол-во «Интересных страниц»", user -> user.getPagesCount().doubleValue());

    @Getter
    private final String id;
    @Getter
    private final String label;
    private final Function<VkUser, Double> getter;

    public static VkUserProperty valueByLabel(String label) {
        for (VkUserProperty property : values()) {
            if (property.getLabel().equals(label)) {
                return property;
            }
        }
        throw new IllegalArgumentException("No VkUserProperty with label: " + label);
    }

    public Double get(VkUser vkUser) {
        return getter.apply(vkUser);
    }
}
