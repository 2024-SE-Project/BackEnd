package hgu.se.raonz.teamUser.application.service;

import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.team.domain.repository.TeamRepository;
import hgu.se.raonz.team.presentation.request.TeamRequest;
import hgu.se.raonz.teamUser.domain.entity.TeamUser;
import hgu.se.raonz.teamUser.domain.repository.TeamUserRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamUserService {
    private final TeamUserRepository teamUserRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Long> addTeamUser(Long teamId, String emailList) {
        Team team = teamRepository.findTeamByTeamId(teamId);

        if (team == null || emailList == null) return null;
        String[] emailListSplitByComma = emailList.split(",");
        List<Long> teamUserIdList = new ArrayList<>();

        for (String s : emailListSplitByComma) {
            if (teamUserRepository.findTeamUserByEmailAndTeamId(s.trim(), teamId) != null) continue;
            TeamUser teamUser = teamUserRepository.save(TeamUser.toAdd(team, s));
            teamUserIdList.add(teamUser.getId());
        }

        return teamUserIdList;
    }

    @Transactional
    public List<Long> deleteTeamUser(Long teamId, String emailList) {
        String[] emailListSplitByComma = emailList.split(",");
        List<Long> returnIdList = new ArrayList<>();
        for (String s : emailListSplitByComma) {
            TeamUser teamUser = teamUserRepository.findTeamUserByEmailAndTeamId(s, teamId);
            if (teamUser == null) continue;
            returnIdList.add(teamUser.getId());
            teamUserRepository.delete(teamUser);
        }

        return returnIdList;
    }



}
