package hgu.se.raonz.team.application.service;

import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.team.application.dto.TeamDto;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.team.domain.repository.TeamRepository;
import hgu.se.raonz.team.presentation.request.TeamRequest;
import hgu.se.raonz.team.presentation.request.TeamUpdateRequest;
import hgu.se.raonz.team.presentation.response.TeamRankResponse;
import hgu.se.raonz.team.presentation.response.TeamResponse;
import hgu.se.raonz.teamUser.domain.entity.TeamUser;
import hgu.se.raonz.teamUser.domain.repository.TeamUserRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addTeam(TeamRequest teamRequest, String userId) {
        System.out.println(teamRequest.getName() + ": " + userId);
        Team team = teamRepository.save(Team.toAdd(teamRequest, userId));

        return team.getId();
    }

    @Transactional
    public Long deleteTeam(Long teamId, String userId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if (team == null) return null;
        if (!team.getLeaderId().equals(userId)) {
            System.out.println(userId);
            return null;
        }
        Long returnId = team.getId();
        teamRepository.delete(team);

        return returnId;
    }

    @Transactional
    public Long updateTeam(Long teamId, TeamUpdateRequest teamUpdateRequest, String userId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if (team == null) return null;

        if (!team.getLeaderId().equals(userId)) {
            System.out.println(userId + " is not LeaderId");
            return null;
        }
        team.setName(teamUpdateRequest.getName());
        team.setLeaderId(teamUpdateRequest.getLeaderId());
        teamRepository.save(team);

        return team.getId();
    }

    @Transactional
    public TeamResponse getTeamResponse(Long teamId, String userId) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team == null) return null;
        System.out.println("Success to find Team");
        List<TeamUser> teamUserList = teamUserRepository.findTeamUserById(teamId);
        List<String> emailList = new ArrayList<>();
        for (TeamUser u: teamUserList) emailList.add(u.getUserEmail());
        return TeamResponse.toResponse(team, emailList);
    }

    @Transactional
    public List<TeamResponse> getTeamResponseList(int index) {
        index --;
        List<Team> teamList = teamRepository.findAll();
        teamList.sort(Comparator.comparing(Team::getId).reversed());
        List<TeamResponse> teamResponseList = new ArrayList<>();
        for (int i = index * 10; i < index * 10 + 10 && i < teamList.size(); i++) {
            Long teamId = teamList.get(i).getId();
            List<TeamUser> teamUserList = teamUserRepository.findTeamUserById(teamId);
            List<String> emailList = new ArrayList<>();
            for (TeamUser u: teamUserList) emailList.add(u.getUserEmail());
            teamResponseList.add(TeamResponse.toResponse(teamList.get(i), emailList));
        }
        return teamResponseList;
    }

    @Transactional
    public List<TeamResponse> getMyTeamResponseList(String userId) {
        User user = userRepository.findUserByUserId(userId);
        List<TeamUser> myTeamUserList = teamUserRepository.findTeamUserListByEmail(user.getEmail());

        List<TeamResponse> teamResponseList = new ArrayList<>();
        for (TeamUser teamUser : myTeamUserList) {
            Long teamId = teamUser.getTeam().getId();
            List<TeamUser> teamUserList = teamUserRepository.findTeamUserById(teamId);
            List<String> emailList = new ArrayList<>();
            for (TeamUser u : teamUserList) emailList.add(u.getUserEmail());
            teamResponseList.add(TeamResponse.toResponse(teamUser.getTeam(), emailList));
        }
        teamResponseList.sort(Comparator.comparing(TeamResponse::getId).reversed());
        return teamResponseList;
    }

    @Transactional
    public boolean isLeader(Long teamId, String leaderId) {
        Team team = teamRepository.findTeamByTeamId(teamId);
        if (team == null) return false;
        return team.getLeaderId().equals(leaderId);
    }

    @Transactional
    public List<TeamRankResponse> getTeamRankResponseList() {
        List<Team> teamList = teamRepository.findAll();
        teamList.sort(Comparator.comparing(Team::getTotalLikeCount).reversed());

        List <TeamRankResponse> teamRankResponseList = new ArrayList<>();
        // top 10
        for (int i = 0; i < 10; i++) {
            teamRankResponseList.add(TeamRankResponse.toResponse(teamList.get(i)));
        }
        return teamRankResponseList;
    }

}
