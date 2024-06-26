package goorm.spoco.domain.testcase.repository;

import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.domain.TestcaseStatus;
import goorm.spoco.global.common.response.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    @Query("select t.input from Testcase t WHERE t.algorithm.algorithmId =: algorithmId AND t.status =: status")
    List<String> findAllByInputWithAlgorithmId(Long algorithmId, Status status);

    @Query("select t.input from Testcase t WHERE t.algorithm.algorithmId =: algorithmId AND t.status =: status")
    List<String> findAllByOutputWithAlgorithmId(Long algorithmId, Status status);

    Optional<Testcase> findByTestcaseIdAndStatus(Long testcaseId, Status status);

    List<Testcase> findAllByAlgorithm_AlgorithmIdAndStatus(Long algorithmId, Status status);
}
