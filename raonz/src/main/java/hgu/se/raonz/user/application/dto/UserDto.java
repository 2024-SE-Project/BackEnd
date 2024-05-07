package hgu.se.raonz.user.application.dto;

import hgu.se.raonz.user.domain.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    private String userId;

    private String name;

    private Long studentId;

    private String email;

    public static UserDto toResponse(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .studentId(user.getStudentId())
                .email(user.getEmail())
                .build();
    }
}
