package hgu.se.raonz.team.presentation.controller;


import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;
import hgu.se.raonz.team.application.service.TeamService;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.team.presentation.request.TeamRequest;
import hgu.se.raonz.team.presentation.request.TeamUpdateRequest;
import hgu.se.raonz.team.presentation.response.TeamResponse;
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

    @DeleteMapping("/team/delete/{teamId}") // 삭제
    public ResponseEntity<Long> deleteTeam(@PathVariable Long teamId, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);

        Long returnId = teamService.deleteTeam(teamId, user_id);

        return ResponseEntity.ok(returnId);
    }

    @PatchMapping("/team/update/{teamId}")
    public ResponseEntity<Long> updatePost(@PathVariable Long teamId, @RequestBody TeamUpdateRequest teamUpdateRequest, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        Long returnId = teamService.updateTeam(teamId, teamUpdateRequest, user_id);

        return ResponseEntity.ok(returnId);
    }

    @GetMapping("/team/get/{id}")
    public ResponseEntity<TeamResponse> getTeamResponse(@PathVariable Long id, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        TeamResponse teamResponse = teamService.getTeamResponse(id, user_id);

        return ResponseEntity.ok(teamResponse);
    }

    @GetMapping("/team/get/all/{index}")
    public ResponseEntity<List<TeamResponse>> getTeamResponseAll(@PathVariable int index, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        // Return the top 10 posts with the highest postId among posts with 1 post type in the order of highest order
        List<TeamResponse> teamResponseList = teamService.getTeamResponseList(index);

        return ResponseEntity.ok(teamResponseList);
    }

    @GetMapping("/my/team/get")
    public ResponseEntity<List<TeamResponse>> getMyTeamResponseAll(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        List<TeamResponse> teamResponseList = teamService.getMyTeamResponseList(user_id);

        return ResponseEntity.ok(teamResponseList);
    }

}
