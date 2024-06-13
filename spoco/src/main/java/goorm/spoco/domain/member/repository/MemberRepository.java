package goorm.spoco.domain.member.repository;


import goorm.spoco.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String memberEmail);
    Optional<Member> findByMemberId(Long memberId);
}

