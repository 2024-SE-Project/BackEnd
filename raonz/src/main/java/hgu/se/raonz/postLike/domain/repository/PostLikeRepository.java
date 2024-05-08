package hgu.se.raonz.postLike.domain.repository;

import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.postLike.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Query("select r from PostLike r where r.user.id = :uid")
    List<PostLike> findPostLikeByUserId(@Param("uid") String uid);

    @Query("select r from PostLike r where r.user.id = :userId and r.post.id = :postId")
    PostLike findPostLikeByUserIdAndPostId(@Param("userId") String userId, @Param("postId") Long postId);
}
