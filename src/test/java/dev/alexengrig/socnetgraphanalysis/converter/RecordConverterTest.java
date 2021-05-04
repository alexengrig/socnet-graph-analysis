package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.clustering.Parameters;
import dev.alexengrig.socnetgraphanalysis.clustering.Record;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecordConverterTest {

    static final RecordConverter CONVERTER = new RecordConverter();

    @Test
    void should_convert() {
        VkUser source = VkUser.builder()
                .accessed(false)
                .age(1)
                .sex(2)
                .city(3)
                .country(4)
                .friendStatus(5)
                .relation(6)
                .political(7)
                .religion(8)
                .lifeMain(9)
                .peopleMain(10)
                .smoking(11)
                .alcohol(12)
                .commonFriendsCount(13)
                .friendsCount(14)
                .followersCount(15)
                .audiosCount(16)
                .videosCount(17)
                .photosCount(18)
                .groupsCount(19)
                .albumsCount(20)
                .notesCount(21)
                .pagesCount(22)
                .build();

        Record record = CONVERTER.convert(source);

        assertNotNull(record, "Record is null");
        Parameters parameters = record.getParameters();
        assertEquals(source.isAccessed() ? 1d : 0d, parameters.get("Accessed"), "Accessed is incorrect");
        assertEquals(source.getAge().doubleValue(), parameters.get("Age"), "Age is incorrect");
        assertEquals(source.getSex().doubleValue(), parameters.get("Sex"), "Sex is incorrect");
        assertEquals(source.getCity().doubleValue(), parameters.get("City"), "City is incorrect");
        assertEquals(source.getCountry().doubleValue(), parameters.get("Country"), "Country is incorrect");
        assertEquals(source.getFriendStatus().doubleValue(), parameters.get("Friend status"), "Friend status is incorrect");
        assertEquals(source.getRelation().doubleValue(), parameters.get("Relation"), "Relation is incorrect");
        assertEquals(source.getPolitical().doubleValue(), parameters.get("Political"), "Political is incorrect");
        assertEquals(source.getReligion().doubleValue(), parameters.get("Religion"), "Religion is incorrect");
        assertEquals(source.getLifeMain().doubleValue(), parameters.get("Life main"), "Life main is incorrect");
        assertEquals(source.getPeopleMain().doubleValue(), parameters.get("People main"), "People main is incorrect");
        assertEquals(source.getSmoking().doubleValue(), parameters.get("Smoking"), "Smoking is incorrect");
        assertEquals(source.getAlcohol().doubleValue(), parameters.get("Alcohol"), "Alcohol is incorrect");
        assertEquals(source.getCommonFriendsCount().doubleValue(), parameters.get("Common friends count"), "Common friends count is incorrect");
        assertEquals(source.getFriendsCount().doubleValue(), parameters.get("Friends count"), "Friends count is incorrect");
        assertEquals(source.getFollowersCount().doubleValue(), parameters.get("Followers count"), "Followers count is incorrect");
        assertEquals(source.getAudiosCount().doubleValue(), parameters.get("Audios count"), "Audios count is incorrect");
        assertEquals(source.getVideosCount().doubleValue(), parameters.get("Videos count"), "Videos count is incorrect");
        assertEquals(source.getPhotosCount().doubleValue(), parameters.get("Photos count"), "Photos count is incorrect");
        assertEquals(source.getGroupsCount().doubleValue(), parameters.get("Groups count"), "Groups count is incorrect");
        assertEquals(source.getAlbumsCount().doubleValue(), parameters.get("Albums count"), "Albums count is incorrect");
        assertEquals(source.getNotesCount().doubleValue(), parameters.get("Notes count"), "Notes count is incorrect");
        assertEquals(source.getPagesCount().doubleValue(), parameters.get("Pages count"), "Pages count is incorrect");
    }
}