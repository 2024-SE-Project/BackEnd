package hgu.se.raonz.postLike.application.dto;

import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.postLike.domain.entity.PostLike;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.user.application.dto.UserDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PostLikeDto {
    private Long id;

    private UserDto user;

    private PostDto post;

    public static PostLikeDto toResponse(PostLike postLike) {
        return PostLikeDto.builder()
                .id(postLike.getId())
                .user(UserDto.toResponse(postLike.getUser()))
                .post(PostDto.toResponse(postLike.getPost()))
                .build();
    }
}
