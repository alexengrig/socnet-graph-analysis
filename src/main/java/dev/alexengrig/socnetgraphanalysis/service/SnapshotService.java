package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import reactor.core.publisher.Mono;

public interface SnapshotService {

    Mono<Snapshot> save(Snapshot snapshot);

    Mono<Snapshot> getById(Long snapshotId);
}
