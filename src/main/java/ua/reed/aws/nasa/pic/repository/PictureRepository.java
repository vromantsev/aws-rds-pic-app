package ua.reed.aws.nasa.pic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.reed.aws.nasa.pic.entity.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    @Query("select p from Picture p where p.size = (select max(pic.size) from Picture pic)")
    Picture findByMaxSize();

}
