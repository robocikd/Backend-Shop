package pl.robocikd.shop.admin.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.admin.review.model.AdminReview;
import pl.robocikd.shop.admin.review.service.AdminReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/reviews")
public class AdminReviewController {
    private final AdminReviewService adminReviewService;

    @GetMapping
    public List<AdminReview> getReviews(){
        return adminReviewService.getReviews();
    }

    @PutMapping("/{id}/moderate")
    public void moderate(@PathVariable Long id){
        adminReviewService.moderate(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        adminReviewService.delete(id);
    }
}
