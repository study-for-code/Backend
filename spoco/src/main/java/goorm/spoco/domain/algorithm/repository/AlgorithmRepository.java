package goorm.spoco.domain.algorithm.repository;

import goorm.spoco.domain.algorithm.domain.Algorithm;
import goorm.spoco.global.common.response.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {

    List<Algorithm> findAllByStatus(Status status);

    Optional<Algorithm> findByAlgorithmIdAndStatus(Long id, Status status);

    //== 제목% 으로 검색 ==//
    @Query("SELECT a FROM Algorithm a WHERE a.title LIKE CONCAT('%-', :title) AND a.status = :status ORDER BY a.title")
    List<Algorithm> findAlgorithmsByOnlyTitle(String title, Status status);

    //== 번호% or %제목% 검색 , 추후에  ==//
    List<Algorithm> findAlgorithmsByTitleLikeAndStatus(String Title, Status status);

}