package hgu.se.raonz.post.application.dto;


import hgu.se.raonz.comment.application.dto.CommentDto;
import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.post.domain.entity.PostFile;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostFileDto {
    private Long postFileId;
    private String imageUrl;
    private String objectName;

    public static PostFileDto toDto(PostFile postFile) {
        return PostFileDto.builder()
                .postFileId(postFile.getId())
                .imageUrl(postFile.getImageUrl())
                .objectName(postFile.getObjectName())
                .build();
    }
}
