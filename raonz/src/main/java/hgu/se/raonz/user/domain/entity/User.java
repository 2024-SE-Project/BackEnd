package hgu.se.raonz.user.domain.entity;

import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.commons.entity.BaseEntity;
import hgu.se.raonz.commons.security.Authority;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.teamUser.domain.entity.TeamUser;
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
    private String id;

    private String name;

    private Long studentId;

    private String email;
    private String phoneNumber;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Post post;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Scrap> scrapList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList;




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
                .id(userRequest.getUserId())
                .email(userRequest.getPhoneNumber())
                .studentId(userRequest.getStudentId())
                .build();
    }

//    public void deletePost(Post post) {
//        for (Post p: postList) {
//            if (p.getPostId().equals(post.getPostId())) {
//                postList.remove(p);
//                return;
//            }
//        }
//    }
}
