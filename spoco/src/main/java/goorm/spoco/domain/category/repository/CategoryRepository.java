package goorm.spoco.domain.category.repository;

import goorm.spoco.domain.category.domain.Category;
import goorm.spoco.domain.category.domain.CategoryStatus;
import goorm.spoco.domain.study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByTitleAndCategoryStatusAndStudy(String title, CategoryStatus status ,Study study);

    Optional<Category> findByCategoryIdAndCategoryStatus(Long id, CategoryStatus status);
}
