package hgu.se.raonz.team.presentation.controller;


import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.team.application.service.TeamService;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.team.presentation.request.TeamRequest;
import hgu.se.raonz.teamUser.application.service.TeamUserService;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.presentation.request.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final TeamUserService teamUserService;
    private final JWTProvider jwtProvider;


    @PostMapping("/team/add")
    public ResponseEntity<Long> addTeam(@RequestBody TeamRequest teamRequest, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        Long teamId = teamService.addTeam(teamRequest, user_id);
        System.out.println("email List: " + teamRequest.getEmailList());
        if (teamRequest.getEmailList() != null) {
            List<Long> teamUserIdList = teamUserService.addTeamUser(teamId, teamRequest.getEmailList());
            System.out.println(teamUserIdList.size());
        }

        return ResponseEntity.ok(teamId);
    }

}
