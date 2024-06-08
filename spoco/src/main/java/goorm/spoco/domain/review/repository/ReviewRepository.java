package goorm.spoco.domain.review.repository;

import goorm.spoco.domain.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByMemberIdAndCodeId(Long memberId, Long codeId);
}
