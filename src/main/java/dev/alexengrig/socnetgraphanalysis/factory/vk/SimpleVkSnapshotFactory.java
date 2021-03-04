package dev.alexengrig.socnetgraphanalysis.factory.vk;

import dev.alexengrig.socnetgraphanalysis.entity.Man;
import dev.alexengrig.socnetgraphanalysis.entity.Person;
import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import dev.alexengrig.socnetgraphanalysis.service.vk.VkUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SimpleVkSnapshotFactory implements VkSnapshotFactory {

    private final VkUserService vkUserService;

    @Override
    public Mono<Snapshot> createByUserId(String userId) {
        return Mono.justOrEmpty(vkUserService.getUserById(userId))
                .map(vkUser -> Person.builder()
                        .userId(vkUser.getId().toString())
                        .name(vkUser.getFirstName() + " " + vkUser.getLastName())
                        .friends(getUserFriendsByUserId(vkUser.getId()))
                        .audiosCount(vkUser.getAudiosCount())
                        .build())
                .map(person -> Snapshot.builder()
                        .person(person)
                        .created(LocalDateTime.now())
                        .build());
    }

    private List<Man> getUserFriendsByUserId(Integer userId) {
        return vkUserService.getUserFriendsById(userId).stream()
                .map(vkUser -> Man.builder()
                        .userId(vkUser.getId().toString())
                        .name(vkUser.getFirstName() + " " + vkUser.getLastName())
                        .audiosCount(vkUser.getAudiosCount())
                        .build())
                .collect(Collectors.toList());
    }
}
