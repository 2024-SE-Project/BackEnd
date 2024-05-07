package hgu.se.raonz.post.presentation.response;

import hgu.se.raonz.comment.application.dto.CommentDto;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.user.domain.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long postId;
    //    private String teamId; // 나중에 필요하면 추가
    private String title;
    private String content;
    //    private String fileAddressList;
    private int type;
    private User user;
    private List<CommentDto> commentList;

    public static PostResponse toResponse(Post post) {
        System.out.println("=====>4");
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .user(post.getUser())
                .commentList(post.getCommentList().stream().map(CommentDto::toDto).toList())
                .build();
    }

    public static PostResponse toListResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .user(post.getUser())
                .build();
    }
}
