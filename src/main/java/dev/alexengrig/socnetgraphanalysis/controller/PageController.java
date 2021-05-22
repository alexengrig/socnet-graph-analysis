package dev.alexengrig.socnetgraphanalysis.controller;

import dev.alexengrig.socnetgraphanalysis.model.ClusteringConditionModel;
import dev.alexengrig.socnetgraphanalysis.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("propertyOptions", pageService.getPropertyOptions());
        return "index";
    }

    @PostMapping("/clustering")
    public String clustering(@ModelAttribute("condition") ClusteringConditionModel condition, Model model) {
        model.addAttribute("clustering", pageService.clustering(condition));
        return "clustering";
    }
}
