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

    private final Randomizer randomizer;
    private final FirstNameRandomizer firstNameRandomizer;
    private final LastNameRandomizer lastNameRandomizer;

    public VkUser randomVkUser(Integer id, String firstName, String lastName, int sex) {

        int friendsCount = randomizer.randomInt(0, 1000);
        return VkUser.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .age(randomizer.randomInt(14, 64))
                .sex(sex)
                .city(0)
                .country(0)
                .friendStatus(0)
                .relation(randomizer.randomInt(0, 8))
                .political(randomizer.randomInt(0, 8))
                .religion(randomizer.randomInt(0, 8))
                .lifeMain(randomizer.randomInt(0, 7))
                .peopleMain(randomizer.randomInt(0, 5))
                .smoking(randomizer.randomInt(0, 4))
                .alcohol(randomizer.randomInt(0, 4))
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
        return new Man(firstName, lastName, isMale);
    }

    @Data
    @AllArgsConstructor
    private static class Man {
        private String firstName;
        private String lastName;
        private boolean male;

        public String getName() {
            return firstName + " " + lastName;
        }
    }
}
