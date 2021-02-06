package dev.alexengrig.socnetgraphanalysis.vk;

import com.vk.api.sdk.objects.users.UserXtrCounters;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class VkMain {

    public static final String FIRST_USER_ID = "0";
    /**
     * App ID.
     * <p>
     * &lt;a href="https://vk.com/apps?act=manage"&gt;Manage app | Settings&lt;/a&gt;.
     */
    public static final int APP_ID = 0;
    /**
     * Service token.
     * <p>
     * &lt;a href="https://vk.com/apps?act=manage"&gt;Manage app | Settings&lt;/a&gt;.
     */
    public static final String APP_TOKEN = "0";
    public static final VkService vk = new VkService(APP_ID, APP_TOKEN);
    public static final ForkJoinPool pool = new ForkJoinPool(16);
    public static volatile long START;

    public static void main(String[] args) throws InterruptedException {
        UserXtrCounters firstUser = vk.getUser(FIRST_USER_ID);
        Integer firstUserId = firstUser.getId();
        Map<Integer, Boolean> allUsers = new ConcurrentHashMap<>();
        START = System.nanoTime();
        pool.execute(new Task(firstUserId, firstUserId, allUsers));
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }

    static class Task extends RecursiveAction {

        private static final int THRESHOLD = 30;

        private final Integer targetId;
        private final List<Integer> userIds;
        private final Map<Integer, Boolean> users;

        public Task(Integer targetId, Integer userId, Map<Integer, Boolean> users) {
            this(targetId, Collections.singletonList(userId), users);
        }

        public Task(Integer targetId, List<Integer> userIds, Map<Integer, Boolean> users) {
            this.targetId = targetId;
            this.userIds = userIds;
            this.users = users;
        }

        @Override
        protected void compute() {
            if (userIds.size() > THRESHOLD) split();
            else perform();
        }

        private void split() {
            int half = userIds.size() / THRESHOLD;
            new Task(targetId, userIds.subList(0, half), users).fork();
            new Task(targetId, userIds.subList(half + 1, userIds.size()), users).compute();
        }

        private void perform() {
            for (Integer userId : userIds) {
                if (users.containsKey(userId)) {
                    return;
                } else {
                    users.put(userId, true);
                }
                List<Integer> friendIds = vk.getFriendIds(userId);
                new Task(targetId, friendIds, users).fork();
                String url = "https://vk.com/id" + userId;
                if (vk.getFollowerIds(userId).contains(targetId)) {
                    System.out.println("Follower: " + url);
                }
                System.out.println("Users: " + users.size());
            }
        }
    }
}
