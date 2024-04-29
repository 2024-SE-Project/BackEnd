package hgu.se.raonz.user.presentation.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    private String name;
    private Long studentId;
    private String phoneNumber;
}
