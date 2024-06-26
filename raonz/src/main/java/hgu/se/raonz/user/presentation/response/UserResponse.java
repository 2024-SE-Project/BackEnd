package hgu.se.raonz.user.presentation.response;

import hgu.se.raonz.user.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserResponse {
    private String name;
    private String userId;
    private String email;
    private String token;
    private String rc;

    public static UserResponse toResponse(User user, String token) {
        return UserResponse.builder()
                .name(user.getName())
                .rc(user.getRC())
                .userId(user.getId())
                .email(user.getEmail())
                .token(token)
                .build();
    }
}
