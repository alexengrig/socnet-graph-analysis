package dev.alexengrig.socnetgraphanalysis.service;

import com.vk.api.sdk.client.ApiRequest;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import dev.alexengrig.socnetgraphanalysis.converter.VkUserConverter;
import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.domain.VkUserProperty;
import dev.alexengrig.socnetgraphanalysis.exception.VkSdkException;
import dev.alexengrig.socnetgraphanalysis.exception.VkUserServiceException;
import dev.alexengrig.socnetgraphanalysis.factory.VkQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkUserService {

    private final VkQueryFactory vkQueryFactory;
    private final VkUserConverter vkUserConverter;

    public Optional<VkUser> getUserById(String code, String vkUserId, Set<VkUserProperty> properties) {
        Objects.requireNonNull(vkUserId, "Vk user id must not be null");
        List<Fields> fields = getFields(properties);
        var query = vkQueryFactory.usersGetQuery(code).userIds(vkUserId).fields(fields);
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

    public List<VkUser> getUsersByIds(String code, List<String> vkUserIds, Set<VkUserProperty> properties) {
        List<Fields> fields = getFields(properties);
        var query = vkQueryFactory.usersGetQuery(code).userIds(vkUserIds).fields(fields);
        var users = execute(query, () -> "getting users by ids: " + vkUserIds);
        return users.stream().map(vkUserConverter::convert).collect(Collectors.toList());
    }

    public List<VkUser> getUserFriendsById(String code, Integer vkUserId, Set<VkUserProperty> properties) {
        var query = vkQueryFactory.friendsGetQuery(code).userId(vkUserId);
        var friends = execute(query, () -> "getting user friends by id: " + vkUserId).getItems();
        return getUsersByIds(code, friends.stream().map(Object::toString).collect(Collectors.toList()), properties);
    }

    private List<Fields> getFields(Set<VkUserProperty> properties) {
        List<Fields> fields = new ArrayList<>();
        if (properties.contains(VkUserProperty.AGE)) {
            fields.add(Fields.BDATE);
        }
        if (properties.contains(VkUserProperty.SEX)) {
            fields.add(Fields.SEX);
        }
        if (properties.contains(VkUserProperty.CITY)) {
            fields.add(Fields.CITY);
        }
        if (properties.contains(VkUserProperty.COUNTRY)) {
            fields.add(Fields.COUNTRY);
        }
        if (properties.contains(VkUserProperty.FRIEND_STATUS)) {
            fields.add(Fields.FRIEND_STATUS);
        }
        if (properties.contains(VkUserProperty.RELATION)) {
            fields.add(Fields.RELATION);
        }
        if (properties.contains(VkUserProperty.POLITICAL)
                || properties.contains(VkUserProperty.RELIGION)
                || properties.contains(VkUserProperty.LIFE_MAIN)
                || properties.contains(VkUserProperty.PEOPLE_MAIN)
                || properties.contains(VkUserProperty.SMOKING)
                || properties.contains(VkUserProperty.ALCOHOL)) {
            fields.add(Fields.PERSONAL);
        }
        if (properties.contains(VkUserProperty.COMMON_FRIENDS_COUNT)) {
            fields.add(Fields.COMMON_COUNT);
        }
        if (properties.contains(VkUserProperty.AUDIOS_COUNT)
                || properties.contains(VkUserProperty.VIDEOS_COUNT)
                || properties.contains(VkUserProperty.PHOTOS_COUNT)
                || properties.contains(VkUserProperty.GROUPS_COUNT)
                || properties.contains(VkUserProperty.ALBUMS_COUNT)
                || properties.contains(VkUserProperty.NOTES_COUNT)
                || properties.contains(VkUserProperty.PAGES_COUNT)) {
            fields.add(Fields.COUNTERS);
        }
        return fields;
    }

    private <T> T execute(ApiRequest<T> request, Supplier<String> errorMessageSupplier) {
        try {
            return request.execute();
        } catch (ApiException | ClientException exception) {
            throw new VkSdkException("Exception of " + errorMessageSupplier.get(), exception);
        }
    }
}
