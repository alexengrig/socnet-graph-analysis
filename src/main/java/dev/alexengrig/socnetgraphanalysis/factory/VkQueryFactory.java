package dev.alexengrig.socnetgraphanalysis.factory;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import dev.alexengrig.socnetgraphanalysis.exception.VkSdkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VkQueryFactory {

    private final VkApiClient client;
    private final Integer appId;
    private final String clientSecret;
    private final String redirectUrl;

    public VkQueryFactory(VkApiClient client,
                          @Value("${application.vk.app-id}") Integer appId,
                          @Value("${application.vk.access-token}") String clientSecret,
                          @Value("${application.vk.oauth.redirect-url}") String redirectUrl) {
        this.client = client;
        this.appId = appId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
    }

    public UserAuthResponse auth(String code) {
        try {
            return client.oAuth()
                    .userAuthorizationCodeFlow(appId, clientSecret, redirectUrl, code)
                    .execute();
        } catch (ApiException | ClientException e) {
            throw new VkSdkException("Exception of authentication", e);
        }
    }

    public UserActor userActor(String code) {
        UserAuthResponse auth = auth(code);
        return new UserActor(auth.getUserId(), auth.getAccessToken());
    }

    public UsersGetQuery usersGetQuery(String code) {
        UserActor actor = userActor(code);
        return new UsersGetQuery(client, actor);
    }

    public FriendsGetQuery friendsGetQuery(String code) {
        UserActor actor = userActor(code);
        return new FriendsGetQuery(client, actor);
    }
}
