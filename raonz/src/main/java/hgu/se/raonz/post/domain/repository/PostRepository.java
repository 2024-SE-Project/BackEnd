package hgu.se.raonz.post.domain.repository;

import hgu.se.raonz.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {

//    Post findPostByPostID(Long postId);
}
