package dev.alexengrig.socnetgraphanalysis.vk;

import com.vk.api.sdk.objects.users.UserXtrCounters;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class VkMain {

    public static final String FIRST_USER_ID = "0";
    public static final int APP_ID = 0;
    public static final String APP_TOKEN = "0";
    public static final VkService vk = new VkService(APP_ID, APP_TOKEN);
    public static final ForkJoinPool pool = new ForkJoinPool(8);
    public static volatile long START;

    public static void main(String[] args) throws InterruptedException {
        UserXtrCounters firstUser = vk.getUser(FIRST_USER_ID);
        Integer firstUserId = firstUser.getId();
        Map<Integer, Boolean> allUsers = new ConcurrentHashMap<>();
        START = System.nanoTime();
        pool.execute(new Task(firstUserId, firstUserId, allUsers));
        pool.awaitTermination(1, TimeUnit.HOURS);
    }

    static class Task extends RecursiveAction {

        private final Integer targetId;
        private final Integer userId;
        private final Map<Integer, Boolean> users;

        public Task(Integer targetId, Integer userId, Map<Integer, Boolean> users) {
            this.targetId = targetId;
            this.userId = userId;
            this.users = users;
        }

        @Override
        protected void compute() {
            if (users.containsKey(userId)) {
                return;
            } else {
                users.put(userId, true);
            }
            List<Integer> friendIds = vk.getFriendIds(userId);
            for (Integer friendId : friendIds) {
                Task task = new Task(targetId, friendId, users);
                task.fork();
            }
            String url = "https://vk.com/id" + userId;
            if (vk.getFollowerIds(userId).contains(targetId)) {
                System.out.println("Follower: " + url);
            }
            System.out.println("Users: " + users.size());
            if (users.size() > 10_000) {
                throw new RuntimeException("Success: " + (System.nanoTime() - START));
            }
        }
    }
}
