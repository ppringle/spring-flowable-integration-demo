package flowable.api;

import flowable.domain.Approval;
import flowable.domain.Article;
import flowable.service.ArticleWorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleWorkflowController {
    @Autowired
    private ArticleWorkflowService articleWorkflowService;
 
    @PostMapping("/submit")
    public void submit(@RequestBody Article article) {
        articleWorkflowService.startProcess(article);
    }
 
    @GetMapping("/tasks")
    public List<Article> getTasks(@RequestParam String assignee) {
        return articleWorkflowService.getTasks(assignee);
    }
 
    @PostMapping("/review")
    public void review(@RequestBody Approval approval) {
        articleWorkflowService.submitReview(approval);
    }

}