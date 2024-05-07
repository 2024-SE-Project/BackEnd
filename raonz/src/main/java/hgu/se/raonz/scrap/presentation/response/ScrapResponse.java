package hgu.se.raonz.scrap.presentation.response;


import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ScrapResponse {
    private Long scrapId;
    private PostDto post;

    public static ScrapResponse toResponse(ScrapDto scrap) {
        return ScrapResponse.builder()
                .scrapId(scrap.getId())
                .post(scrap.getPost())
                .build();
    }
}
