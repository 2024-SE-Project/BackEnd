package hgu.se.raonz.postLike.presentation.response;

import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.postLike.application.dto.PostLikeDto;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.presentation.response.ScrapResponse;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostLikeResponse {
    private Long postLikeId;
    private PostDto post;

    public static PostLikeResponse toResponse(PostLikeDto postLike) {
        return PostLikeResponse.builder()
                .postLikeId(postLike.getId())
                .post(postLike.getPost())
                .build();
    }
}
