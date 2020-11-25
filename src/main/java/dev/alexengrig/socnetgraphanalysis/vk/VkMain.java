package dev.alexengrig.socnetgraphanalysis.vk;

import com.vk.api.sdk.objects.users.UserXtrCounters;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class VkMain {

    public static final String FIRST_USER_ID = "0";
    public static final int APP_ID = 0;
    public static final String APP_TOKEN = "0";
    public static final VkService vk = new VkService(APP_ID, APP_TOKEN);
    public static final ForkJoinPool pool = new ForkJoinPool(6);
    public static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        UserXtrCounters firstUser = vk.getUser(FIRST_USER_ID);
        Integer firstUserId = firstUser.getId();
        Map<Integer, Boolean> allUsers = new ConcurrentHashMap<>();
        allUsers.put(firstUserId, true);
        ConcurrentLinkedQueue<UserXtrCounters> queue = new ConcurrentLinkedQueue<>(Collections.singleton(firstUser));
        while (true) {
            UserXtrCounters user = queue.poll();
            if (user != null) {
                executor.execute(() -> {
                    List<String> friendIds = vk.getFriendIds(user.getId());
                    List<UserXtrCounters> friends = vk.getUsers(friendIds);
                    if (!friends.isEmpty()) {
                        try {
                            queue.addAll(pool.submit(() -> friends.parallelStream()
                                    .filter(u -> {
                                        Integer userId = u.getId();
                                        if (allUsers.containsKey(userId)) {
                                            return false;
                                        }
                                        String url = "https://vk.com/id" + userId;
                                        if (vk.getFollowerIds(userId).contains(firstUserId)) {
                                            System.out.println("Follower: " + url);
                                        }
                                        allUsers.put(userId, true);
                                        return true;
                                    })
                                    .collect(Collectors.toList()))
                                    .get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Queue: " + queue.size() + "\tUsers: " + allUsers.size());
                    }
                });
            }
        }
    }
}
