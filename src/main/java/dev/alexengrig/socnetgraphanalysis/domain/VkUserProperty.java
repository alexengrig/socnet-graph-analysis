package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public enum VkUserProperty {

    ACCESSED("accessed", "Accessed", user -> user.isAccessed() ? 1d : 0d),
    AGE("age", "Age", user -> user.getAge().doubleValue()),
    SEX("sex", "Sex", user -> user.getSex().doubleValue()),
    CITY("city", "City", user -> user.getCity().doubleValue()),
    COUNTRY("country", "Country", user -> user.getCountry().doubleValue()),
    FRIEND_STATUS("friend_status", "Friend status", user -> user.getFriendStatus().doubleValue()),
    RELATION("relation", "Relation", user -> user.getRelation().doubleValue()),
    POLITICAL("political", "Political", user -> user.getPolitical().doubleValue()),
    RELIGION("religion", "Religion", user -> user.getReligion().doubleValue()),
    LIFE_MAIN("life_main", "Life main", user -> user.getLifeMain().doubleValue()),
    PEOPLE_MAIN("people_main", "People main", user -> user.getPeopleMain().doubleValue()),
    SMOKING("smoking", "Smoking", user -> user.getSmoking().doubleValue()),
    ALCOHOL("alcohol", "Alcohol", user -> user.getAlcohol().doubleValue()),
    COMMON_FRIENDS_COUNT("common_friends_count", "Common friends count", user -> user.getCommonFriendsCount().doubleValue()),
    FRIENDS_COUNT("friends_count", "Friends count", user -> user.getFriendsCount().doubleValue()),
    FOLLOWERS_COUNT("followers_count", "Followers count", user -> user.getFollowersCount().doubleValue()),
    AUDIOS_COUNT("audios_count", "Audios count", user -> user.getAudiosCount().doubleValue()),
    VIDEOS_COUNT("videos_count", "Videos count", user -> user.getVideosCount().doubleValue()),
    PHOTOS_COUNT("photos_count", "Photos count", user -> user.getPhotosCount().doubleValue()),
    GROUPS_COUNT("groups_count", "Groups count", user -> user.getGroupsCount().doubleValue()),
    ALBUMS_COUNT("albums_count", "Albums count", user -> user.getAlbumsCount().doubleValue()),
    NOTES_COUNT("notes_count", "Notes count", user -> user.getNotesCount().doubleValue()),
    PAGES_COUNT("pages_count", "Pages count", user -> user.getPagesCount().doubleValue());

    @Getter
    private final String id;
    @Getter
    private final String label;
    private final Function<VkUser, Double> getter;

    public Double get(VkUser vkUser) {
        return getter.apply(vkUser);
    }
}
