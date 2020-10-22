package dev.alexengrig.socnetgraphanalysis.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class VkMain {

    public static final int APP_ID = 0;
    public static final String APP_TOKEN = "0";
    public static final ServiceActor ACTOR = new ServiceActor(APP_ID, APP_TOKEN);
    public static final HttpTransportClient TRANSPORT_CLIENT = HttpTransportClient.getInstance();
    public static final VkApiClient VK_CLIENT = new VkApiClient(TRANSPORT_CLIENT);
    public static final UsersGetQuery GET_USER = new UsersGetQuery(VK_CLIENT, ACTOR);
    public static final FriendsGetQuery GET_FRIENDS = new FriendsGetQuery(VK_CLIENT, ACTOR);

    public static void main(String[] args) throws ClientException, ApiException {
        UserXtrCounters firstUser = getUserById("0");
        Set<Integer> allUsers = new HashSet<>(Collections.singleton(firstUser.getId()));
        Queue<UserXtrCounters> queue = new ConcurrentLinkedQueue<>(Collections.singleton(firstUser));
        while (!queue.isEmpty()) {
            UserXtrCounters user = queue.poll();
            List<String> friendIds = getFriendIdsByUserId(user.getId());
            List<UserXtrCounters> friends = getUsersByIds(friendIds);
            queue.addAll(friends.stream()
                    .filter(u -> {
                        if (allUsers.contains(u.getId())) {
                            return false;
                        }
                        System.out.println("https://vk.com/id" + u.getId());
                        return true;
                    })
                    .collect(Collectors.toList()));
        }
    }

    private static UserXtrCounters getUserById(String id) throws ClientException, ApiException {
        return GET_USER.userIds(id).execute().get(0);
    }

    private static List<String> getFriendIdsByUserId(Integer userId) {
        try {
            return GET_FRIENDS.userId(userId).execute()
                    .getItems().stream().map(String::valueOf).collect(Collectors.toList());
        } catch (ApiException | ClientException e) {
            return Collections.emptyList();
        }
    }

    private static List<UserXtrCounters> getUsersByIds(List<String> ids) {
        try {
            return GET_USER.userIds(ids).execute();
        } catch (ApiException | ClientException e) {
            return Collections.emptyList();
        }
    }
}
