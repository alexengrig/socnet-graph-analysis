package dev.alexengrig.socnetgraphanalysis.configuration;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkConfiguration {

    @Bean
    public TransportClient transportClient() {
        return new HttpTransportClient();
    }

    @Bean
    public VkApiClient vkApiClient(TransportClient transportClient) {
        return new VkApiClient(transportClient);
    }

    @Bean
    public ServiceActor serviceActor(@Value("${application.vk.app-id}") String appId,
                                     @Value("${application.vk.access-token}") String accessToken) {
        return new ServiceActor(Integer.valueOf(appId), accessToken);
    }

    /* Queries */

    @Bean
    public UsersGetQuery usersGetQuery(VkApiClient client, ServiceActor actor) {
        return new UsersGetQuery(client, actor);
    }

    @Bean
    public FriendsGetQuery friendsGetQuery(VkApiClient client, ServiceActor actor) {
        return new FriendsGetQuery(client, actor);
    }
}
