package hgu.se.raonz.teamUser.presentation.controller;


import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.team.application.service.TeamService;
import hgu.se.raonz.team.presentation.request.TeamRequest;
import hgu.se.raonz.teamUser.application.service.TeamUserService;
import hgu.se.raonz.teamUser.presentation.request.TeamUserRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class TeamUserController {

    private final TeamUserService teamUserService;
    private final TeamService teamService;
    private final JWTProvider jwtProvider;


    @PostMapping("/team-user/add/{teamId}")
    public ResponseEntity<List<Long>> addTeamUser(@PathVariable Long teamId, @RequestBody TeamUserRequest teamUserRequest, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        // 팀장인지 확인
        if (!teamService.isLeader(teamId, user_id)) {
            System.out.println("Only Team Leader can add members");
            return null;
        }
        // 팀에 User 추가
        List<Long> teamUserIdList = teamUserService.addTeamUser(teamId, teamUserRequest.getEmailList());

        return ResponseEntity.ok(teamUserIdList);
    }

    @DeleteMapping("/team-user/delete/{teamId}") // 삭제
    public ResponseEntity<List<Long>> deleteTeamUser(@PathVariable Long teamId, @RequestBody TeamUserRequest teamUserRequest, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        // 팀장인지 확인
        if (!teamService.isLeader(teamId, user_id)) {
            System.out.println("Only Team Leader can add members");
            return null;
        }
        List<Long> returnIdList = teamUserService.deleteTeamUser(teamId, teamUserRequest.getEmailList());

        return ResponseEntity.ok(returnIdList);
    }
}
