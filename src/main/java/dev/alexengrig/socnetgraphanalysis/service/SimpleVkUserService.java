package dev.alexengrig.socnetgraphanalysis.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.exception.VkSdkException;
import dev.alexengrig.socnetgraphanalysis.exception.VkUserServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleVkUserService implements VkUserService {

    private final ConversionService conversionService;
    private final UsersGetQuery usersGetQuery;
    private final FriendsGetQuery friendsGetQuery;

    @Override
    public Optional<VkUser> getUserById(Integer vkUserId) {
        Objects.requireNonNull(vkUserId, "Vk user id must not be null");
        try {
            var users = usersGetQuery.userIds(vkUserId.toString()).execute();
            if (users.size() == 1) {
                var user = users.get(0);
                VkUser convertedUser = conversionService.convert(user, VkUser.class);
                return Optional.ofNullable(convertedUser);
            } else if (users.isEmpty()) {
                return Optional.empty();
            } else {
                log.warn("Found several users with the same id {}: {}",
                        vkUserId,
                        users.stream().map(UserFull::toPrettyString).collect(Collectors.joining(", ", "[", "]")));
                throw new VkUserServiceException("Found several users with the same id: " + vkUserId);
            }
        } catch (ApiException | ClientException exception) {
            throw new VkSdkException("Exception of getting user by id: " + vkUserId, exception);
        }
    }

    @Override
    public List<VkUser> getUserFriendsById(Integer vkUserId) {
        try {
            var friends = friendsGetQuery.userId(vkUserId).execute().getItems();
            return friends.stream()
                    .map(user -> conversionService.convert(user, VkUser.class))
                    .collect(Collectors.toList());
        } catch (ApiException | ClientException exception) {
            throw new VkSdkException("Exception of getting user friends by id: " + vkUserId, exception);
        }
    }
}
