package dev.alexengrig.socnetgraphanalysis.factory;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import dev.alexengrig.socnetgraphanalysis.service.VkAuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkQueryFactory {

    private final VkApiClient client;
    private final VkAuthorizationService authorization;

    public UserActor userActor(String code) {
        UserAuthResponse auth = authorization.auth(code);
        return new UserActor(auth.getUserId(), auth.getAccessToken());
    }

    @Cacheable("vkUserGetQueries")
    public UsersGetQuery usersGetQuery(String code) {
        UserActor actor = userActor(code);
        return new UsersGetQuery(client, actor);
    }

    @Cacheable("vkFriendsGetQueries")
    public FriendsGetQuery friendsGetQuery(String code) {
        UserActor actor = userActor(code);
        return new FriendsGetQuery(client, actor);
    }
}
