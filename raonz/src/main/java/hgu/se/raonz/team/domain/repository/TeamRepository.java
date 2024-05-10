package hgu.se.raonz.team.domain.repository;

import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select r from Team r where r.id = :teamId")
    Team findTeamByTeamId(@Param("teamId") Long teamId);

}
