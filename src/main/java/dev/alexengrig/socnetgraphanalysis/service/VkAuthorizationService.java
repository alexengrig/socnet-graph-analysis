package dev.alexengrig.socnetgraphanalysis.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.UserAuthResponse;
import dev.alexengrig.socnetgraphanalysis.exception.VkSdkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class VkAuthorizationService {

    private final VkApiClient client;
    private final Integer appId;
    private final String clientSecret;
    private final String redirectUrl;

    public VkAuthorizationService(VkApiClient client,
                                  @Value("${application.vk.app-id}") Integer appId,
                                  @Value("${application.vk.access-token}") String clientSecret,
                                  @Value("${application.vk.oauth.redirect-url}") String redirectUrl) {
        this.client = client;
        this.appId = appId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
    }

    @Cacheable("vkUserAuthorizations")
    public UserAuthResponse auth(String code) {
        try {
            return client.oAuth()
                    .userAuthorizationCodeFlow(appId, clientSecret, redirectUrl, code)
                    .execute();
        } catch (ApiException | ClientException e) {
            throw new VkSdkException("Exception of authorization", e);
        }
    }
}
