package dev.alexengrig.socnetgraphanalysis.exception;

public class VkUserNotFoundException extends RuntimeException {

    public VkUserNotFoundException(String vkUserId) {
        super("Vk user not found with id: " + vkUserId);
    }
}
