package dev.alexengrig.socnetgraphanalysis.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VkUser {
    private Integer id;
    private String firstName;
    private String lastName;
    private String birthday;
    private Integer age;

    private boolean accessed;

    private Integer sex;
    private Integer city;
    private Integer country;
    private Integer friendStatus;

    private Integer relation;
    private Integer political;
    private Integer religion;
    private Integer lifeMain;
    private Integer peopleMain;
    private Integer smoking;
    private Integer alcohol;

    private Integer commonFriendsCount;
    private Integer friendsCount;
    private Integer followersCount;
    private Integer audiosCount;
    private Integer videosCount;
    private Integer photosCount;
    private Integer groupsCount;
    private Integer albumsCount;
    private Integer notesCount;
    private Integer pagesCount;
}
