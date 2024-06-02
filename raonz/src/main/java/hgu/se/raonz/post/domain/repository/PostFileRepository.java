package hgu.se.raonz.post.domain.repository;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {

    @Query("select r from PostFile r where r.post.id = :postId")
    List<PostFile> findByPostId(@Param("postId") Long postId);
}
