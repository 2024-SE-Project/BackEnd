package hgu.se.raonz.teamUser.application.dto;


import hgu.se.raonz.team.application.dto.TeamDto;
import hgu.se.raonz.team.domain.entity.Team;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamUserDto {
    private Long id;

    private String userEmail;

    private TeamDto team;
}
