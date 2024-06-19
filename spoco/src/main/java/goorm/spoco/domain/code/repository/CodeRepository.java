package goorm.spoco.domain.code.repository;

import goorm.spoco.domain.code.domain.Code;
import goorm.spoco.global.common.response.Status;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

    Optional<Code> findByCodeIdAndStatus(Long codeId, Status status);

    Optional<Code> findByAlgorithm_AlgorithmIdAndMember_MemberIdAndStatus(Long algorithmId, Long memberId, Status status);
}