package hgu.se.raonz.scrap.domain.repository;



import hgu.se.raonz.scrap.domain.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query("select r from Scrap r where r.user.id = :uid")
    List<Scrap> findScrapByUserId(@Param("uid") String uid);
}
