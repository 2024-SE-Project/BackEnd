package hgu.se.raonz.team.application.dto;


import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.teamUser.application.dto.TeamUserDto;
import hgu.se.raonz.teamUser.domain.entity.TeamUser;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamDto {
    private Long id;
    private String name;
    private String leaderId;
    private List<String> emailList;

    public static TeamDto toResponse(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .leaderId(team.getLeaderId())
                .build();
    }
}
