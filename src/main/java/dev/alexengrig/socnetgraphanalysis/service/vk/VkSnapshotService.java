package dev.alexengrig.socnetgraphanalysis.service.vk;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import reactor.core.publisher.Mono;

public interface VkSnapshotService {

    Mono<Snapshot> createByUserId(String vkUserId);
}
