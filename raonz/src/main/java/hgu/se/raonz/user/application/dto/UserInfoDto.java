package hgu.se.raonz.user.application.dto;

import hgu.se.raonz.post.presentation.response.PostResponse;
import hgu.se.raonz.postLike.application.dto.PostLikeDto;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.user.domain.entity.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserInfoDto {
    private String userId;
    private String name;
    private Long studentId;
    private String email;
    private List<ScrapDto> scrapDtoList;
    private List<PostLikeDto> postLikeDtoList;
    private List<PostResponse> postResponseList;

    public static UserInfoDto toResponse(User user, List<ScrapDto> scrapDtoList, List<PostLikeDto> postLikeDtoList, List<PostResponse> postResponseList) {
        return UserInfoDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .studentId(user.getStudentId())
                .email(user.getEmail())
                .scrapDtoList(scrapDtoList)
                .postLikeDtoList(postLikeDtoList)
                .postResponseList(postResponseList)
                .build();
    }

    public static UserInfoDto toResponse(User user, List<ScrapDto> scrapDtoList, List<PostLikeDto> postLikeDtoList) {
        return UserInfoDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .studentId(user.getStudentId())
                .email(user.getEmail())
                .scrapDtoList(scrapDtoList)
                .postLikeDtoList(postLikeDtoList)
                .build();
    }
}
