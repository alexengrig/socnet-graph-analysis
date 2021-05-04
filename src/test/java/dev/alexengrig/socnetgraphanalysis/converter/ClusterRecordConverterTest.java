package dev.alexengrig.socnetgraphanalysis.converter;

import dev.alexengrig.socnetgraphanalysis.clustering.Parameters;
import dev.alexengrig.socnetgraphanalysis.domain.ClusterRecord;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClusterRecordConverterTest {

    static final ClusterRecordConverter CONVERTER = new ClusterRecordConverter();

    @Test
    void should_convert() {
        VkUser source = VkUser.builder()
                .id(0)
                .firstName("First")
                .lastName("Last")
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

        ClusterRecord record = CONVERTER.convert(source);

        assertNotNull(record,
                "Record is null");
        assertEquals(source.getId(), record.getId(),
                "Id is incorrect");
        assertEquals(String.join(" ", source.getFirstName(), source.getLastName()), record.getLabel(),
                "Label is incorrect");
        Parameters parameters = record.getParameters();
        assertNotNull(parameters,
                "Parameters is null");
        assertEquals(source.isAccessed() ? 1d : 0d, parameters.get("Accessed"),
                "Parameter 'Accessed' is incorrect");
        assertEquals(source.getAge().doubleValue(), parameters.get("Age"),
                "Parameter 'Age' is incorrect");
        assertEquals(source.getSex().doubleValue(), parameters.get("Sex"),
                "Parameter 'Sex' is incorrect");
        assertEquals(source.getCity().doubleValue(), parameters.get("City"),
                "Parameter 'City' is incorrect");
        assertEquals(source.getCountry().doubleValue(), parameters.get("Country"),
                "Parameter 'Country' is incorrect");
        assertEquals(source.getFriendStatus().doubleValue(), parameters.get("Friend status"),
                "Parameter 'Friend status' is incorrect");
        assertEquals(source.getRelation().doubleValue(), parameters.get("Relation"),
                "Parameter 'Relation' is incorrect");
        assertEquals(source.getPolitical().doubleValue(), parameters.get("Political"),
                "Parameter 'Political' is incorrect");
        assertEquals(source.getReligion().doubleValue(), parameters.get("Religion"),
                "Parameter 'Religion' is incorrect");
        assertEquals(source.getLifeMain().doubleValue(), parameters.get("Life main"),
                "Parameter 'Life main' is incorrect");
        assertEquals(source.getPeopleMain().doubleValue(), parameters.get("People main"),
                "Parameter 'People main' is incorrect");
        assertEquals(source.getSmoking().doubleValue(), parameters.get("Smoking"),
                "Parameter 'Smoking' is incorrect");
        assertEquals(source.getAlcohol().doubleValue(), parameters.get("Alcohol"),
                "Parameter 'Alcohol' is incorrect");
        assertEquals(source.getCommonFriendsCount().doubleValue(), parameters.get("Common friends count"),
                "Parameter 'Common friends count' is incorrect");
        assertEquals(source.getFriendsCount().doubleValue(), parameters.get("Friends count"),
                "Parameter 'Friends count' is incorrect");
        assertEquals(source.getFollowersCount().doubleValue(), parameters.get("Followers count"),
                "Parameter 'Followers count' is incorrect");
        assertEquals(source.getAudiosCount().doubleValue(), parameters.get("Audios count"),
                "Parameter 'Audios count' is incorrect");
        assertEquals(source.getVideosCount().doubleValue(), parameters.get("Videos count"),
                "Parameter 'Videos count' is incorrect");
        assertEquals(source.getPhotosCount().doubleValue(), parameters.get("Photos count"),
                "Parameter 'Photos count' is incorrect");
        assertEquals(source.getGroupsCount().doubleValue(), parameters.get("Groups count"),
                "Parameter 'Groups count' is incorrect");
        assertEquals(source.getAlbumsCount().doubleValue(), parameters.get("Albums count"),
                "Parameter 'Albums count' is incorrect");
        assertEquals(source.getNotesCount().doubleValue(), parameters.get("Notes count"),
                "Parameter 'Notes count' is incorrect");
        assertEquals(source.getPagesCount().doubleValue(), parameters.get("Pages count"),
                "Parameter 'Pages count' is incorrect");
    }
}