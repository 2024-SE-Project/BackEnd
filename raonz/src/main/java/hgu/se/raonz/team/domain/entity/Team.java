package hgu.se.raonz.team.domain.entity;

import hgu.se.raonz.commons.entity.BaseEntity;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.postLike.domain.entity.PostLike;
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
    private String content;
    private String leaderId;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Post> postList;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamUser> teamUserList;

    public static Team toAdd(TeamRequest teamRequest, String userId) {
        return Team.builder()
                .name(teamRequest.getName())
                .content(teamRequest.getContent())
                .leaderId(userId)
                .build();
    }

    public int getTotalLikeCount() {
        int sum = 0;
        for (Post p: postList) {
            sum += p.getPostLikeList().size();
        }
        return sum;
    }

    public int getTotalScrapeCount() {
        int sum = 0;
        for (Post p: postList) {
            sum += p.getScrapList().size();
        }
        return sum;
    }
}
