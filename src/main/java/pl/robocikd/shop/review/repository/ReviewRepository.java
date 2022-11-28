package pl.robocikd.shop.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.robocikd.shop.common.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
