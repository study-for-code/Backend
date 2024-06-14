package goorm.spoco.domain.subscribe.repository;

import goorm.spoco.domain.subscribe.domain.Subscribe;
import goorm.spoco.global.common.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    Optional<Subscribe> findSubscribeBySubscribeIdAndStatus(Long id, Status status);
    Optional<Subscribe> findSubscribeByCategory_CategoryIdAndAlgorithm_AlgorithmIdAndStatus(Long cId, Long aId, Status status);
}
