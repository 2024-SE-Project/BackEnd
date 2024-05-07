package hgu.se.raonz.comment.domain.entity;

import hgu.se.raonz.comment.presentation.request.CommentRequest;
import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList;

    public static Comment toAdd(Post post, User user, CommentRequest commentRequest) {
        return Comment.builder()
                .user(user)
                .post(post)
                .contents(commentRequest.getContents())
                .build();
    }
}
