package hgu.se.raonz.user.presentation.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String userId;
    private String phoneNumber;
    private Long studentId;
    private String email;
}
