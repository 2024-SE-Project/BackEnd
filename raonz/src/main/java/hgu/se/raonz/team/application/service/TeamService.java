package hgu.se.raonz.team.application.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {
    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileName;
    @Value("${spring.cloud.gcp.storage.bucket}") // application.yml에 써둔 bucket 이름
    private String bucketName;
    private final TeamRepository teamRepository;
    private final TeamUserRepository teamUserRepository;
    private final UserRepository userRepository;
    InputStream keyFile;
    Storage storage;

    @Transactional
    public Long addTeam(TeamRequest teamRequest, String userId) {
        System.out.println(teamRequest.getName() + ": " + userId);
        Team team;
        try {
            keyFile = ResourceUtils.getURL(keyFileName).openStream();
            storage = (Storage) StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(keyFile))
                    .build()
                    .getService();
            String objectName = UUID.randomUUID() + "_" + teamRequest.getImg().getOriginalFilename();

            // 버킷 객체 가져오기
            Bucket bucket = storage.get(bucketName);

            // 객체 생성 및 업로드
            Blob blob = bucket.create(objectName, teamRequest.getImg().getInputStream(), teamRequest.getImg().getContentType());

            // 업로드된 객체의 url 만들기
            String uploadedImageUrl = "https://storage.cloud.google.com/" + bucketName + "/" + objectName;

            team = teamRepository.save(Team.toAdd(teamRequest, uploadedImageUrl, objectName, userId));
            keyFile.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
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
    public Long updateTeam(Long teamId, TeamRequest teamRequest, String userId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        team.setName(teamRequest.getName());
        team.setContent(teamRequest.getContent());
        teamRepository.save(team);
        try {
            keyFile = ResourceUtils.getURL(keyFileName).openStream();
            storage = (Storage) StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(keyFile))
                    .build()
                    .getService();
            String objectName = UUID.randomUUID() + "_" + teamRequest.getImg().getOriginalFilename();

            // 버킷 객체 가져오기
            Bucket bucket = storage.get(bucketName);

            // 객체 생성 및 업로드
            Blob blob = bucket.create(objectName, teamRequest.getImg().getInputStream(), teamRequest.getImg().getContentType());

            // 업로드된 객체의 url 만들기
            String uploadedImageUrl = "https://storage.cloud.google.com/" + bucketName + "/" + objectName;

            keyFile.close();
            team.setImgURL(uploadedImageUrl);
            team.setImgName(objectName);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return team.getId();
        }
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
        int size = teamRankResponseList.size();
        if (size > 10) size = 10;
        // top 10
        for (int i = 0; i < size ; i++) {
            teamRankResponseList.add(TeamRankResponse.toResponse(teamList.get(i)));
        }
        return teamRankResponseList;
    }

}
