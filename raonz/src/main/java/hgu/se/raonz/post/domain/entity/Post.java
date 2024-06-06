package hgu.se.raonz.post.domain.entity;

import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.commons.entity.BaseEntity;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.postLike.domain.entity.PostLike;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.team.domain.entity.Team;
import hgu.se.raonz.user.domain.entity.User;
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
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private String content;

//    private String fileAddressList;
    private int type;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team; // 나중에 필요하면 추가

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Scrap> scrapList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikeList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostFile> postFileList;

    public static Post toAdd(PostRequest postRequest, int type, User user) {
        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .type(type)
                .user(user)
                .build();
    }
    public int getLikeCount() {
        return postLikeList.size();
    }





}
