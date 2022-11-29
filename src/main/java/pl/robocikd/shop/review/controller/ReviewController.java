package pl.robocikd.shop.review.controller;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.robocikd.shop.common.model.Review;
import pl.robocikd.shop.review.controller.dto.ReviewDto;
import pl.robocikd.shop.review.service.ReviewService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto) {
        return reviewService.addReview(Review.builder()
                .authorName(cleanContent(reviewDto.authorName()))
                .content(cleanContent(reviewDto.content()))
                .productId(reviewDto.productId())
                .moderated(false)
                .build());
    }

    private String cleanContent(String content) {
        return Jsoup.clean(content, Safelist.none());
    }
}
