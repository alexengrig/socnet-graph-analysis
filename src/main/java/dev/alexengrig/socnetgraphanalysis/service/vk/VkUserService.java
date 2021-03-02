package dev.alexengrig.socnetgraphanalysis.service.vk;

import dev.alexengrig.socnetgraphanalysis.domain.VkUser;

import java.util.List;
import java.util.Optional;

public interface VkUserService {

    Optional<VkUser> getUserById(Integer vkUserId);

    Optional<VkUser> getUserById(String vkUserId);

    List<VkUser> getUserFriendsById(Integer vkUserId);
}
