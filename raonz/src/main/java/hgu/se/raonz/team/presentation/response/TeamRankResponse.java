package hgu.se.raonz.team.presentation.response;

import hgu.se.raonz.team.application.dto.TeamDto;
import hgu.se.raonz.team.domain.entity.Team;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamRankResponse {
    private Long id;
    private String name;
    private String leaderId;
    private int likeCount;
    private int scrapeCount;

    public static TeamRankResponse toResponse(Team team) {
        return TeamRankResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .leaderId(team.getLeaderId())
                .likeCount(team.getTotalLikeCount())
                .scrapeCount(team.getTotalScrapeCount())
                .build();
    }
}
