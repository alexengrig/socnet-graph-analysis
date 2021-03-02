package dev.alexengrig.socnetgraphanalysis.factory.vk;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import reactor.core.publisher.Mono;

public interface VkSnapshotFactory {

    Mono<Snapshot> createByUserId(String userId);
}
