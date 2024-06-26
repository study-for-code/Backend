package goorm.spoco.domain.join.repository;

import goorm.spoco.domain.join.domain.Join;
import goorm.spoco.global.common.response.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JoinRepository extends JpaRepository<Join, Long> {
    Optional<Join> findByMember_MemberIdAndStudy_StudyId(Long memberId, Long studyId);

    List<Join> findAllByStudy_StudyIdAndStatus(Long studyId, Status status);
}
