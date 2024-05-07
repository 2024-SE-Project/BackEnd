package hgu.se.raonz.scrap.application.dto;

import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.user.application.dto.UserDto;
import hgu.se.raonz.user.domain.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ScrapDto {

    private Long id;

    private UserDto user;

    private PostDto post;

    public static ScrapDto toResponse(Scrap scrap) {
        return ScrapDto.builder()
                .id(scrap.getId())
                .user(UserDto.toResponse(scrap.getUser()))
                .post(PostDto.toResponse(scrap.getPost()))
                .build();
    }
}
