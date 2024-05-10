package hgu.se.raonz.teamUser.domain.repository;

import hgu.se.raonz.postLike.domain.entity.PostLike;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.teamUser.domain.entity.TeamUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long>  {
    @Query("select r from TeamUser r where r.userEmail = :email and r.team.id = :teamId")
    TeamUser findTeamUserByEmailAndTeamId(@Param("email") String email, @Param("teamId") Long teamId);

}
