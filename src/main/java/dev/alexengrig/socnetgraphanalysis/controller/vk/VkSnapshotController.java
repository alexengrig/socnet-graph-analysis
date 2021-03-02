package dev.alexengrig.socnetgraphanalysis.controller.vk;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import dev.alexengrig.socnetgraphanalysis.service.vk.VkSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/snapshots/vk")
@RequiredArgsConstructor
public class VkSnapshotController {

    private final VkSnapshotService vkSnapshotService;

    @PostMapping("/{vkUserId}")
    public Mono<Snapshot> createSnapshotByVkUserId(@PathVariable String vkUserId) {
        return vkSnapshotService.createByUserId(vkUserId);
    }
}
