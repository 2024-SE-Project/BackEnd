package hgu.se.raonz.team.application.service;

import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.team.domain.repository.TeamRepository;
import hgu.se.raonz.team.presentation.request.TeamRequest;
import hgu.se.raonz.user.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    @Transactional
    public Long addTeam(TeamRequest teamRequest, String userId) {
        System.out.println(teamRequest.getName() + ": " + userId);
        Team team = teamRepository.save(Team.toAdd(teamRequest.getName(), userId));

        return team.getId();
    }

    @Transactional
    public boolean isLeader(Long teamId, String leaderId) {
        Team team = teamRepository.findTeamByTeamId(teamId);
        if (team == null) return false;
        return team.getLeaderId().equals(leaderId);
    }

}
