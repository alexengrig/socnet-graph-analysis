package dev.alexengrig.socnetgraphanalysis.controller;

import dev.alexengrig.socnetgraphanalysis.model.ClusteringCondition;
import dev.alexengrig.socnetgraphanalysis.model.Parent;
import dev.alexengrig.socnetgraphanalysis.service.ClusteringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final ClusteringService clusteringService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("condition", new ClusteringCondition("doubleclick"));
        return "index";
    }

    @PostMapping("/clustering")
    public String clustering(@ModelAttribute("condition") ClusteringCondition clusteringCondition, Model model) {
        String vkUserId = clusteringCondition.getVkUserId();
        model.addAttribute("vkUserId", vkUserId);
        Parent clustering = clusteringService.kMeans(vkUserId);
        model.addAttribute("clustering", clustering);
        return "clustering";
    }
}
