package dev.alexengrig.socnetgraphanalysis.controller;

import dev.alexengrig.socnetgraphanalysis.model.ClusteringConditionModel;
import dev.alexengrig.socnetgraphanalysis.service.PageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final String vkOAuthUrl;
    private final PageService pageService;

    @GetMapping
    public String index(@RequestParam(value = "code", required = false) String code, Model model) {
        if (isNull(code)) {
            return "redirect:" + vkOAuthUrl;
        }
        model.addAttribute("code", code);
        model.addAttribute("propertyOptions", pageService.getPropertyOptions());
        return "index";
    }

    @PostMapping("/clustering")
    public String clustering(@RequestParam(value = "code", required = false) String code,
                             @ModelAttribute("condition") ClusteringConditionModel condition,
                             Model model) {
        if (isNull(code)) {
            return "redirect:" + vkOAuthUrl;
        }
        model.addAttribute("clustering", pageService.clustering(code, condition));
        return "clustering";
    }
}
