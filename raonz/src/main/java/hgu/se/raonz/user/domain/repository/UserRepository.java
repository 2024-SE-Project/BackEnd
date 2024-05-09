package hgu.se.raonz.user.domain.repository;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("select r from User r where r.id = :userId")
    List<User> findUserListByUserId(String userId);

    List<Scrap> findScrapListByUserId(String userId);
}
