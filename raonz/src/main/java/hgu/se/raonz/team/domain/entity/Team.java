package hgu.se.raonz.team.domain.entity;

import hgu.se.raonz.commons.entity.BaseEntity;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.team.presentation.request.TeamRequest;
import hgu.se.raonz.teamUser.domain.entity.TeamUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String leaderId;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamUser> teamUserList;

    public static Team toAdd(String teamName, String userId) {
        return Team.builder()
                .name(teamName)
                .leaderId(userId)
                .build();
    }
}