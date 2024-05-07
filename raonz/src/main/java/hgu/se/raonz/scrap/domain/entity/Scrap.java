package hgu.se.raonz.scrap.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public static Scrap toAdd(User user, Post post) {
        return Scrap.builder()
                .user(user)
                .post(post)
                .build();
    }
}
