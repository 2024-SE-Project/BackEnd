package hgu.se.raonz.user.application.dto;

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
    private List<ScrapDto> scrapList;

    public static UserInfoDto toResponse(User user) {
        return UserInfoDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .studentId(user.getStudentId())
                .email(user.getEmail())
                .build();
    }
}
