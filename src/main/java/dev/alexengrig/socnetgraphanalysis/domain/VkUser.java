package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VkUser {

    @EqualsAndHashCode.Include
    Integer id;
    String firstName;
    String lastName;
    String birthday;
    Integer age;

    boolean accessed;

    String cityName;
    String countryName;

    Integer sex;
    Integer city;
    Integer country;
    Integer friendStatus;

    Integer relation;
    Integer political;
    Integer religion;
    Integer lifeMain;
    Integer peopleMain;
    Integer smoking;
    Integer alcohol;

    Integer commonFriendsCount;
    Integer friendsCount;
    Integer followersCount;
    Integer audiosCount;
    Integer videosCount;
    Integer photosCount;
    Integer groupsCount;
    Integer albumsCount;
    Integer notesCount;
    Integer pagesCount;
}
