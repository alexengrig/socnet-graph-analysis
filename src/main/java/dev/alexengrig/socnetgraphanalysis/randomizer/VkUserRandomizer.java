package dev.alexengrig.socnetgraphanalysis.randomizer;

import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VkUserRandomizer {

    private static final List<Integer> RELIGION_IDS = List.of(167, 102, 101, 107, 124, 129, 139, 200, 201);

    private final Randomizer randomizer;
    private final LastNameRandomizer lastNameRandomizer;
    private final FirstNameRandomizer firstNameRandomizer;
    private final CityRandomizer cityRandomizer;
    private final CountryRandomizer countryRandomizer;

    public List<VkUser> randomVkUsers(int count) {
        List<Man> men = getMen(count);
        AtomicInteger id = new AtomicInteger(randomizer.randomInt(345567789, 876543210));
        return men.stream()
                .map(man -> randomVkUser(id.getAndIncrement(), man.getFirstName(), man.getLastName(), man.isMale() ? 2 : 1))
                .collect(Collectors.toList());
    }

    private List<Man> getMen(int count) {
        List<Man> men = new ArrayList<>(count);
        Set<String> cache = new HashSet<>();
        for (int i = 0; i < count; i++) {
            Man man;
            do {
                man = randomMan();
            } while (cache.contains(man.getName()));
            cache.add(man.getName());
            men.add(man);
        }
        return men;
    }

    private Man randomMan() {
        boolean isMale = randomizer.randomBoolean();
        String firstName = isMale ? firstNameRandomizer.randomMale() : firstNameRandomizer.randomFemale();
        String lastName = isMale ? lastNameRandomizer.randomMale() : lastNameRandomizer.randomFemale();
        return new Man(isMale, firstName, lastName);
    }

    private VkUser randomVkUser(Integer id, String firstName, String lastName, int sex) {
        int friendsCount = randomizer.randomInt(1, 1000);
        CountryRandomizer.Country country = countryRandomizer.randomCountry();
        CityRandomizer.City city = cityRandomizer.randomCity(country);
        return VkUser.builder()
                .id(id)
                .accessed(true)
                .firstName(firstName)
                .lastName(lastName)
                .age(randomizer.randomInt(14, 64))
                .sex(sex)
                .country(country.getId())
                .countryName(country.getName())
                .city(city.getId())
                .cityName(city.getName())
                .friendStatus(randomizer.randomInt(0, 3))
                .relation(randomizer.randomInt(0, 8))
                .political(randomizer.randomInt(0, 9))
                .religion(randomizer.randomOfList(RELIGION_IDS))
                .lifeMain(randomizer.randomInt(0, 8))
                .peopleMain(randomizer.randomInt(0, 6))
                .smoking(randomizer.randomInt(0, 5))
                .alcohol(randomizer.randomInt(0, 5))
                .commonFriendsCount(randomizer.randomInt(0, friendsCount))
                .friendsCount(friendsCount)
                .followersCount(randomizer.randomInt(0, 1000))
                .audiosCount(randomizer.randomInt(0, 700))
                .videosCount(randomizer.randomInt(0, 500))
                .photosCount(randomizer.randomInt(0, 500))
                .groupsCount(randomizer.randomInt(0, 100))
                .albumsCount(randomizer.randomInt(0, 10))
                .notesCount(randomizer.randomInt(0, 10))
                .pagesCount(randomizer.randomInt(0, 20))
                .build();
    }

    @Data
    @AllArgsConstructor
    private static class Man {

        private boolean male;
        private String firstName;
        private String lastName;

        public String getName() {
            return firstName + " " + lastName;
        }
    }
}
