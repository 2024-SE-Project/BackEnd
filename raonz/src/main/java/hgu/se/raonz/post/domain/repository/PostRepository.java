package hgu.se.raonz.post.domain.repository;

import hgu.se.raonz.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select r from Post r where r.type = :type")
    List<Post> findPostListByType(@Param("type") int type);
}
