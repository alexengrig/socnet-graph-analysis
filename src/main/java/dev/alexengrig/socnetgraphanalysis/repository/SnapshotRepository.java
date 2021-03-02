package dev.alexengrig.socnetgraphanalysis.repository;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnapshotRepository extends ReactiveNeo4jRepository<Snapshot, Long> {
}
