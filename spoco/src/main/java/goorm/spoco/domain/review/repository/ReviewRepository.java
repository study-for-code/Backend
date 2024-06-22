package goorm.spoco.domain.review.repository;

import goorm.spoco.domain.review.domain.Review;
import goorm.spoco.global.common.response.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewIdAndStatus(Long reviewId, Status status);
    List<Review> findAllByCode_CodeIdAndStatus(Long codeId, Status status);
}
