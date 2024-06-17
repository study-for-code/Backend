package goorm.spoco.domain.subscribe.repository;

import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.global.common.response.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Optional<Subscribe> findBySubscribeIdAndStatus(Long subscribeId, Status status);

    List<Subscribe> findAllByCategory_CategoryIdAndStatus(Long categoryId, Status status);
}
