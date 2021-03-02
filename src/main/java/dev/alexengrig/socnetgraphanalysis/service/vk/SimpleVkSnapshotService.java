package dev.alexengrig.socnetgraphanalysis.service.vk;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import dev.alexengrig.socnetgraphanalysis.factory.vk.VkSnapshotFactory;
import dev.alexengrig.socnetgraphanalysis.service.SnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SimpleVkSnapshotService implements VkSnapshotService {

    private final VkSnapshotFactory vkSnapshotFactory;
    private final SnapshotService snapshotService;

    @Override
    public Mono<Snapshot> createByUserId(String vkUserId) {
        return vkSnapshotFactory.createByUserId(vkUserId).flatMap(snapshotService::save);
    }
}
