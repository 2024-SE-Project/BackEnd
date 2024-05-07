package hgu.se.raonz.comment.application.dto;

import hgu.se.raonz.comment.domain.entity.Comment;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {
    private String contents;
    private Long id;

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .build();
    }
}
