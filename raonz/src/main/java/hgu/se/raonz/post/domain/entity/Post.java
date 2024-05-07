package hgu.se.raonz.post.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hgu.se.raonz.commons.entity.BaseEntity;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;


//    private String teamId; // 나중에 필요하면 추가
    private String title;
    private String content;
//    private String fileAddressList;
    private int type;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    public static Post toAdd(PostRequest postRequest, int type, User user) {
        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .type(type)
                .user(user)
                .build();
    }





}
