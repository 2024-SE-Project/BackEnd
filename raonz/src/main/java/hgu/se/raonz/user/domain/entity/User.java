package hgu.se.raonz.user.domain.entity;

import hgu.se.raonz.commons.BaseEntity;
import hgu.se.raonz.user.presentation.request.UserRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    private Long studentId;

    private String phoneNumber;

    public static User toAdd(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .phoneNumber(userRequest.getPhoneNumber())
                .studentId(userRequest.getStudentId())
                .build();
    }

}
