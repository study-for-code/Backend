package goorm.spoco.domain.algorithm.repository;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.domain.algorithm.domain.AlgorithmStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    Optional<Algorithm> findAlgorithmByAlgorithmIdAndAlgorithmStatus(Long id, AlgorithmStatus status);

    List<Algorithm> findAlgorithmsByTitleLike(String title);
}