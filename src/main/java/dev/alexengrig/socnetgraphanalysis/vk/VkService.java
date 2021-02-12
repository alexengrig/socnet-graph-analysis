package dev.alexengrig.socnetgraphanalysis.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.users.responses.GetFollowersResponse;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.users.UsersGetFollowersQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VkService {
    private final VkApiClient client;
    private final ServiceActor actor;

    private transient UsersGetFollowersQuery getFollowersQuery;
    private transient UsersGetQuery getUserQuery;
    private transient FriendsGetQuery getFriendsQuery;


    public VkService(int appId, String appToken) {
        this.client = new VkApiClient(HttpTransportClient.getInstance());
        actor = new ServiceActor(appId, appToken);
    }

    public Set<Integer> getFollowerIds(Integer userId) {
        try {
            GetFollowersResponse response = prepareGetFollowersQuery().userId(userId).execute();
            return new HashSet<>(response.getItems());
        } catch (ApiException | ClientException e) {
            return Collections.emptySet();
        }
    }

    private UsersGetFollowersQuery prepareGetFollowersQuery() {
        if (getFollowersQuery == null) {
            getFollowersQuery = new UsersGetFollowersQuery(client, actor);
        }
        return getFollowersQuery;
    }

    public UserXtrCounters getUser(String userId) {
        try {
            List<UserXtrCounters> response = prepareGetUserQuery().userIds(userId).execute();
            return response.get(0);
        } catch (ApiException | ClientException e) {
            return null;
        }
    }

    public List<UserXtrCounters> getUsers(List<String> userIds) {
        try {
            return prepareGetUserQuery().userIds(userIds).execute();
        } catch (ApiException | ClientException e) {
            return Collections.emptyList();
        }
    }

    private UsersGetQuery prepareGetUserQuery() {
        if (getUserQuery == null) {
            getUserQuery = new UsersGetQuery(client, actor);
        }
        return getUserQuery;
    }

    public Set<Integer> getFriendsIds(Integer userId) {
        try {
            GetResponse response = prepareGetFriendsQuery().userId(userId).execute();
            return new HashSet<>(response.getItems());
        } catch (ApiException | ClientException e) {
            return Collections.emptySet();
        }
    }

    private FriendsGetQuery prepareGetFriendsQuery() {
        if (getFriendsQuery == null) {
            getFriendsQuery = new FriendsGetQuery(client, actor);
        }
        return getFriendsQuery;
    }
}
