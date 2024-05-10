package hgu.se.raonz.teamUser.domain.entity;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.postLike.domain.entity.PostLike;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    public static TeamUser toAdd(Team team, String email) {
        return TeamUser.builder()
                .userEmail(email)
                .team(team)
                .build();
    }
}
