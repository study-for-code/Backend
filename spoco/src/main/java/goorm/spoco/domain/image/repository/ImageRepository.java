package goorm.spoco.domain.image.repository;

import goorm.spoco.domain.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findImageByImageIdAndStudyId(Long imageId, Long studyId);

    List<Image> findImagesByStudyId(Long studyId);
}
