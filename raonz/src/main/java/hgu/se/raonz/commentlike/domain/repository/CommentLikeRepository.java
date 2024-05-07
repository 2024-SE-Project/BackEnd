package hgu.se.raonz.commentlike.domain.repository;

import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    @Query("select r from CommentLike r where r.user.id = :userId and r.comment.id = :commentId")
    CommentLike findCommentLikeByUserIdAndCommentId(@Param("userId") String userId, @Param("commentId") Long commentId);
}
