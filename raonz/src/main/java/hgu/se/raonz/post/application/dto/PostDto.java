package hgu.se.raonz.post.application.dto;

import hgu.se.raonz.post.domain.entity.Post;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private int type;

    public static PostDto toResponse(Post post) {
        return PostDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .type(post.getType())
                .build();
    }
}
