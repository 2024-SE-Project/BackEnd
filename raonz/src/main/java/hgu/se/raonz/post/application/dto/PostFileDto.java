package hgu.se.raonz.post.application.dto;


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
}
