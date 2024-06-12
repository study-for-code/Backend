package goorm.spoco.domain.member.repository;


import goorm.spoco.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String memberEmail); // 지워도 되는지 여쭤보기
    Optional<Member> findByMemberId(Long memberId);
}

