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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VkUserConverterTest {

    static final VkUserConverter CONVERTER = new VkUserConverter();

    @Test
    void should_convert() {
        GetResponse source = new GetResponse();
        source.setId(1);
        source.setFirstName("First name");
        source.setLastName("Second name");
        source.setBdate("1.1.2001");
        source.setSex(Sex.FEMALE);
        City city = new City();
        {
            city.setId(2);
        }
        source.setCity(city);
        Country country = new Country();
        {
            country.setId(3);
        }
        source.setCountry(country);
        source.setFriendStatus(FriendStatusStatus.OUTCOMING_REQUEST);
        source.setRelation(UserRelation.SINGLE);
        Personal personal = new Personal();
        {
            personal.setPolitical(4);
            personal.setReligionId(5);
            personal.setLifeMain(6);
            personal.setPeopleMain(7);
            personal.setSmoking(8);
            personal.setAlcohol(9);
        }
        source.setPersonal(personal);
        source.setCommonCount(10);
        UserCounters counters = new UserCounters();
        {
            counters.setFriends(11);
            counters.setFollowers(12);
            counters.setGroups(13);
            counters.setAudios(14);
            counters.setVideos(15);
            counters.setPhotos(16);
            counters.setAlbums(17);
            counters.setNotes(18);
            counters.setPages(19);
        }
        source.setCounters(counters);

        VkUser user = CONVERTER.convert(source);

        assertNotNull(user, "User is null");
        assertEquals(source.getId(), user.getId(), "Id is incorrect");
        assertEquals(source.getFirstName(), user.getFirstName(), "First name is incorrect");
        assertEquals(source.getLastName(), user.getLastName(), "Last name is incorrect");
        assertEquals(source.getBdate(), user.getBirthday(), "Birthday is incorrect");
        assertEquals(source.getSex().ordinal(), user.getSex(), "Birthday is incorrect");
        assertEquals(source.getCity().getId(), user.getCity(), "City is incorrect");
        assertEquals(source.getCountry().getId(), user.getCountry(), "Country is incorrect");
        assertEquals(source.getFriendStatus().ordinal(), user.getFriendStatus(), "FriendStatus is incorrect");
        assertEquals(source.getRelation().ordinal(), user.getRelation(), "Relation is incorrect");
        assertEquals(source.getPersonal().getPolitical(), user.getPolitical(), "Political is incorrect");
        assertEquals(source.getPersonal().getReligionId(), user.getReligion(), "ReligionId is incorrect");
        assertEquals(source.getPersonal().getLifeMain(), user.getLifeMain(), "LifeMain is incorrect");
        assertEquals(source.getPersonal().getPeopleMain(), user.getPeopleMain(), "PeopleMain is incorrect");
        assertEquals(source.getPersonal().getSmoking(), user.getSmoking(), "Smoking is incorrect");
        assertEquals(source.getPersonal().getAlcohol(), user.getAlcohol(), "Alcohol is incorrect");
        assertEquals(source.getCommonCount(), user.getCommonFriendsCount(), "CommonFriendsCount is incorrect");
        assertEquals(source.getCounters().getFriends(), user.getFriendsCount(), "FriendsCount is incorrect");
        assertEquals(source.getCounters().getFollowers(), user.getFollowersCount(), "FollowersCount is incorrect");
        assertEquals(source.getCounters().getGroups(), user.getGroupsCount(), "GroupsCount is incorrect");
        assertEquals(source.getCounters().getAudios(), user.getAudiosCount(), "AudiosCount is incorrect");
        assertEquals(source.getCounters().getVideos(), user.getVideosCount(), "VideosCount is incorrect");
        assertEquals(source.getCounters().getPhotos(), user.getPhotosCount(), "PhotosCount is incorrect");
        assertEquals(source.getCounters().getAlbums(), user.getAlbumsCount(), "AlbumsCount is incorrect");
        assertEquals(source.getCounters().getNotes(), user.getNotesCount(), "NotesCount is incorrect");
        assertEquals(source.getCounters().getPages(), user.getPagesCount(), "PagesCount is incorrect");
    }
}