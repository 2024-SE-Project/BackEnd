package hgu.se.raonz.scrap.domain.repository;



import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query("select r from Scrap r where r.user.id = :uid")
    List<Scrap> findScrapByUserId(@Param("uid") String uid);

    @Query("select r from Scrap r where r.user.id = :userId and r.post.id = :postId")
    Scrap findScrapByUserIdAndPostId(@Param("userId") String userId, @Param("postId") Long postId);
}
