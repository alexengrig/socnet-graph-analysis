package dev.alexengrig.socnetgraphanalysis.service;

import com.vk.api.sdk.client.ApiRequest;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleVkUserService implements VkUserService {

    private static final Fields[] USER_FIELDS = new Fields[]{
            Fields.BDATE,
            Fields.SEX,
            Fields.CITY,
            Fields.COUNTRY,
            Fields.FRIEND_STATUS,
            Fields.RELATION,
            Fields.PERSONAL,
            Fields.COMMON_COUNT,
            Fields.COUNTERS
    };

    private final ConversionService conversionService;
    private final UsersGetQuery usersGetQuery;
    private final FriendsGetQuery friendsGetQuery;

    @Override
    public Optional<VkUser> getUserById(Integer vkUserId) {
        Objects.requireNonNull(vkUserId, "Vk user id must not be null");
        return getUserById(vkUserId.toString());
    }

    @Override
    public Optional<VkUser> getUserById(String vkUserId) {
        Objects.requireNonNull(vkUserId, "Vk user id must not be null");
        var query = this.usersGetQuery.userIds(vkUserId).fields(USER_FIELDS);
        var users = execute(query, () -> "getting user by id: " + vkUserId);
        if (users.size() == 1) {
            var user = users.get(0);
            var convertedUser = conversionService.convert(user, VkUser.class);
            return Optional.ofNullable(convertedUser);
        } else if (users.isEmpty()) {
            return Optional.empty();
        } else {
            log.warn("Found several users with the same id {}: {}",
                    vkUserId,
                    users.stream().map(UserFull::toPrettyString).collect(Collectors.joining(", ", "[", "]")));
            throw new VkUserServiceException("Found several users with the same id: " + vkUserId);
        }
    }

    @Override
    public List<VkUser> getUserFriendsById(Integer vkUserId) {
        var query = this.friendsGetQuery.userId(vkUserId);
        var friends = execute(query, () -> "getting user friends by id: " + vkUserId).getItems();
        return friends.stream()
                .map(this::getUserById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> conversionService.convert(user, VkUser.class))
                .collect(Collectors.toList());
    }

    private <T> T execute(ApiRequest<T> request, Supplier<String> errorMessageSupplier) {
        try {
            return request.execute();
        } catch (ApiException | ClientException exception) {
            throw new VkSdkException("Exception of " + errorMessageSupplier.get(), exception);
        }
    }
}
