package hgu.se.raonz.post.domain.repository;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}
