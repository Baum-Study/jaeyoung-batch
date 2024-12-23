package study.batch.practice.chapter6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.batch.practice.chapter6.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
