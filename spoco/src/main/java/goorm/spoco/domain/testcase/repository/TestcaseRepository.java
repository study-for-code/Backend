package goorm.spoco.domain.testcase.repository;

import goorm.spoco.domain.testcase.domain.Testcase;
import goorm.spoco.domain.testcase.domain.TestcaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    @Query("select t.input from Testcase t")
    List<String> findAllInput();

    @Query("select t.output from Testcase t")
    List<String> findAllOutput();

    Optional<Testcase> findByTestcaseIdAndTestcaseStatus(Long id, TestcaseStatus status);
}
