package dev.alexengrig.socnetgraphanalysis.configuration;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkConfiguration {

    @Bean
    public String vkOAuthUrl(@Value("${application.vk.app-id}") String clientId,
                             @Value("${application.vk.oauth.redirect-url}") String redirectUrl,
                             @Value("${application.vk.oauth.version}") String version) {
        return "https://oauth.vk.com/authorize?display=page&response_type=code" +
                "&redirect_uri=" + redirectUrl +
                "&v=" + version +
                "&client_id=" + clientId;
    }

    @Bean
    public TransportClient transportClient() {
        return new HttpTransportClient();
    }

    @Bean
    public VkApiClient vkApiClient(TransportClient transportClient) {
        return new VkApiClient(transportClient);
    }
}
