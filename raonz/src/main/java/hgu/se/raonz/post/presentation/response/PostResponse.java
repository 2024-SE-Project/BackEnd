package hgu.se.raonz.post.presentation.response;

import hgu.se.raonz.comment.application.dto.CommentDto;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.user.application.dto.UserDto;
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
    private UserDto userDto;
    private List<CommentDto> commentList;
    private boolean isLike;
    private boolean isScraped;

    public static PostResponse toResponse(Post post, boolean isLike, boolean isScraped) {
        System.out.println("=====>4");
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .userDto(UserDto.toResponse(post.getUser()))
                .commentList(post.getCommentList().stream().map(CommentDto::toDto).toList())
                .isLike(isLike)
                .isScraped(isScraped)
                .build();
    }
}
