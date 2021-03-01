package dev.alexengrig.socnetgraphanalysis.controller;


import dev.alexengrig.socnetgraphanalysis.domain.VkUser;
import dev.alexengrig.socnetgraphanalysis.exception.VkUserNotFoundException;
import dev.alexengrig.socnetgraphanalysis.service.VkUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vk/users")
@RequiredArgsConstructor
public class VkUserController {

    private final VkUserService service;

    @GetMapping("/{vkUserId}")
    public VkUser getUserById(@PathVariable String vkUserId) {
        return service.getUserById(vkUserId).orElseThrow(() -> new VkUserNotFoundException(vkUserId));
    }
}
