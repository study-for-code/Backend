package goorm.spoco.domain.study.repository;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<Study> findByStudyId(Long studyId);

    @Query("SELECT DISTINCT s FROM Study s " +
            "JOIN FETCH s.joins j " +
            "JOIN FETCH j.member m " +
            "WHERE m.memberId = :memberId " +
            "AND j.status = 'ACTIVE' AND s.status = 'ACTIVE'")
    List<Study> findAllByJoinStudy(Long memberId);

    Optional<Study> findByJoinCode(String joinCode);
}