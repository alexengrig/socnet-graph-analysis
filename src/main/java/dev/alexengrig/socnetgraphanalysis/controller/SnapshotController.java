package dev.alexengrig.socnetgraphanalysis.controller;

import dev.alexengrig.socnetgraphanalysis.entity.Snapshot;
import dev.alexengrig.socnetgraphanalysis.service.SnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/snapshots")
@RequiredArgsConstructor
public class SnapshotController {

    private final SnapshotService snapshotService;

    @GetMapping("/{snapshotId}")
    public Mono<Snapshot> getSnapshotById(@PathVariable Long snapshotId) {
        return snapshotService.getById(snapshotId);
    }
}
