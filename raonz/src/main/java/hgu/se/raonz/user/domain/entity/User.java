package hgu.se.raonz.user.domain.entity;

import hgu.se.raonz.commons.entity.BaseEntity;
import hgu.se.raonz.commons.security.Authority;
import hgu.se.raonz.user.presentation.request.UserRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    private String userId;

    private String name;

    private Long studentId;

    private String phoneNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> roles) {
        this.roles = roles;
        roles.forEach(o -> o.setUser(this));

    }

    public static User toAdd(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .userId(userRequest.getUserId())
                .phoneNumber(userRequest.getPhoneNumber())
                .studentId(userRequest.getStudentId())
                .build();
    }

}
