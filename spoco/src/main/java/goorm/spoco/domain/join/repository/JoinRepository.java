package goorm.spoco.domain.join.repository;

import goorm.spoco.domain.join.domain.Join;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JoinRepository extends JpaRepository<Join, Long> {
    Optional<Join> findByMember_MemberIdAndStudy_StudyId(Long memberId, Long studyId);
}
