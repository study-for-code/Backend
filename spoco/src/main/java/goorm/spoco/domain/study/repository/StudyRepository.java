package goorm.spoco.domain.study.repository;

import goorm.spoco.domain.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}