package goorm.spoco.domain.testcase.repository;

import goorm.spoco.domain.testcase.domain.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TestcaseRepository extends JpaRepository<Testcase, Long> {
    @Query("select t.input from Testcase t")
    public List<String> findAllInput();

    @Query("select t.output from Testcase t")
    public List<String> findAllOutput();
}
