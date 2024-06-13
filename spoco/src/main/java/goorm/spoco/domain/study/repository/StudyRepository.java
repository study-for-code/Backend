package goorm.spoco.domain.study.repository;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRepository extends JpaRepository<Study, Long> {

    Optional<Category> findCategoryByTitle(String title);
}