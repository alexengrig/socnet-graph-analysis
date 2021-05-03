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

import java.util.function.Function;

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
        city.setId(2);
        source.setCity(city);
        Country country = new Country();
        country.setId(3);
        source.setCountry(country);
        source.setFriendStatus(FriendStatusStatus.OUTCOMING_REQUEST);
        source.setRelation(UserRelation.SINGLE);
        Personal personal = new Personal();
        personal.setPolitical(4);
        personal.setReligionId(5);
        personal.setLifeMain(6);
        personal.setPeopleMain(7);
        personal.setSmoking(8);
        personal.setAlcohol(9);
        source.setPersonal(personal);
        source.setCommonCount(10);
        UserCounters counters = new UserCounters();
        counters.setFriends(11);
        counters.setFollowers(12);
        counters.setGroups(13);
        counters.setAudios(14);
        counters.setVideos(15);
        counters.setPhotos(16);
        counters.setAlbums(17);
        counters.setNotes(18);
        counters.setPages(19);
        source.setCounters(counters);

        VkUser user = CONVERTER.convert(source);

        assertNotNull(user, "User is null");
        assertUserEquals(source, GetResponse::getId, user, VkUser::getId, "Id");
        assertUserEquals(source, GetResponse::getFirstName, user, VkUser::getFirstName, "First name");
        assertUserEquals(source, GetResponse::getLastName, user, VkUser::getLastName, "Last name");
        assertUserEquals(source, GetResponse::getBdate, user, VkUser::getBirthday, "Birthday");

    }

    public <E, A, V> void assertUserEquals(E expected, Function<E, V> expectedGetter, A actual, Function<A, V> actualGetter, String name) {
        V expectedValue = expectedGetter.apply(expected);
        V actualValue = actualGetter.apply(actual);
        assertEquals(expectedValue, actualValue, name + " is incorrect");
    }

}