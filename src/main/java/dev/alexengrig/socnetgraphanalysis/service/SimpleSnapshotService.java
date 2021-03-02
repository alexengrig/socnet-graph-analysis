package dev.alexengrig.socnetgraphanalysis.service;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import dev.alexengrig.socnetgraphanalysis.repository.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SimpleSnapshotService implements SnapshotService {

    private final SnapshotRepository snapshotRepository;

    @Override
    public Mono<Snapshot> save(Snapshot snapshot) {
        Objects.requireNonNull(snapshot, "Snapshot must not be null");
        return snapshotRepository.save(snapshot);
    }

    @Override
    public Mono<Snapshot> getById(Long snapshotId) {
        Objects.requireNonNull(snapshotId, "Snapshot id must not be null");
        return snapshotRepository.findById(snapshotId);
    }
}
