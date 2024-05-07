package hgu.se.raonz.comment.domain.repository;

import hgu.se.raonz.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
