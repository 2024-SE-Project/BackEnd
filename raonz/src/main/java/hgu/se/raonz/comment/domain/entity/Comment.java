package hgu.se.raonz.comment.domain.entity;

import hgu.se.raonz.comment.presentation.request.CommentRequest;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String contents;

    public static Comment toAdd(Post post, User user, CommentRequest commentRequest) {
        return Comment.builder()
                .user(user)
                .post(post)
                .contents(commentRequest.getContents())
                .build();
    }
}
