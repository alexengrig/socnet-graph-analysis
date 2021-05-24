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
import static java.util.Objects.nonNull;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final String vkOAuthUrl;
    private final PageService pageService;

    private String getOAuthRedirectUrl() {
        return "redirect:" + vkOAuthUrl;
    }

    @GetMapping
    public String index(@RequestParam(value = "code", required = false) String code,
                        @RequestParam(value = "test", required = false) String test,
                        @RequestParam(value = "count", required = false) String count,
                        Model model) {
        if (isNull(code)) {
            return getOAuthRedirectUrl();
        }
        model.addAttribute("code", code);
        model.addAttribute("propertyOptions", pageService.getPropertyOptions());
        if ("test".equals(test)) {
            model.addAttribute("test", test);
        }
        if (nonNull(count)) {
            model.addAttribute("count", count);
        }
        return "index";
    }

    @PostMapping("/clustering")
    public String clustering(@ModelAttribute("condition") ClusteringConditionModel condition, Model model) {
        if (isNull(condition.getCode())) {
            return getOAuthRedirectUrl();
        }
        model.addAttribute("clustering", pageService.clustering(condition));
        return "clustering";
    }
}
