package hgu.se.raonz.team.presentation.response;


import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.presentation.response.ScrapResponse;
import hgu.se.raonz.team.application.dto.TeamDto;
import hgu.se.raonz.team.domain.entity.Team;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamResponse {
    private Long id;
    private String name;
    private String content;
    private String leaderId;
    private List<String> emailList;
    private String imgURL;

    public static TeamResponse toResponse(Team team, List<String> emailList) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .content(team.getContent())
                .leaderId(team.getLeaderId())
                .emailList(emailList)
                .imgURL(team.getImgURL())
                .build();
    }
}
