package hgu.se.raonz.post.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    private String imageUrl;
    private String objectName;

    public static PostFile toAdd(Post post, String imageUrl, String objectName) {
        return PostFile.builder()
                .post(post)
                .imageUrl(imageUrl)
                .objectName(objectName)
                .build();
    }
}
