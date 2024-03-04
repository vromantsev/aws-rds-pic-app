package ua.reed.aws.nasa.pic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.reed.aws.nasa.pic.entity.Camera;

public interface CameraRepository extends JpaRepository<Camera, Long> {
}
