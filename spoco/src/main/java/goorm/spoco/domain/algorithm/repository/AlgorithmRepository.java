package goorm.spoco.domain.algorithm.repository;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AlgorithmRepository extends JpaRepository<Algorithm,Long> {
}
