package dev.alexengrig.socnetgraphanalysis.service;

import com.vk.api.sdk.client.ApiRequest;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import dev.alexengrig.socnetgraphanalysis.converter.VkUserConverter;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.exception.VkSdkException;
import dev.alexengrig.socnetgraphanalysis.exception.VkUserServiceException;
import dev.alexengrig.socnetgraphanalysis.factory.VkQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkUserService {

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

    private final VkQueryFactory vkQueryFactory;
    private final VkUserConverter vkUserConverter;

    public Optional<VkUser> getUserById(String code, Integer vkUserId) {
        Objects.requireNonNull(vkUserId, "Vk user id must not be null");
        return getUserById(code, vkUserId.toString());
    }

    public Optional<VkUser> getUserById(String code, String vkUserId) {
        Objects.requireNonNull(vkUserId, "Vk user id must not be null");
        var query = vkQueryFactory.usersGetQuery(code).userIds(vkUserId).fields(USER_FIELDS);
        var users = execute(query, () -> "getting user by id: " + vkUserId);
        if (users.size() == 1) {
            var user = users.get(0);
            var convertedUser = vkUserConverter.convert(user);
            return Optional.of(convertedUser);
        } else if (users.isEmpty()) {
            return Optional.empty();
        } else {
            log.warn("Found several users with the same id {}: {}",
                    vkUserId,
                    users.stream().map(UserFull::toPrettyString).collect(Collectors.joining(", ", "[", "]")));
            throw new VkUserServiceException("Found several users with the same id: " + vkUserId);
        }
    }

    public List<VkUser> getUserFriendsById(String code, Integer vkUserId) {
        var query = vkQueryFactory.friendsGetQuery(code).userId(vkUserId);
        var friends = execute(query, () -> "getting user friends by id: " + vkUserId).getItems();
        return friends.stream()
                .map(id -> getUserById(code, id))
                .filter(Optional::isPresent)
                .map(Optional::get)
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
