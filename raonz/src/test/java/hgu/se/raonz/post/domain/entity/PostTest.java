package hgu.se.raonz.post.domain.entity;

import hgu.se.raonz.comment.domain.entity.Comment;

import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.user.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostTest {

    @Test
    void testEntityMappingAndGetterSetter() {
        // Create a user for association
        User user = new User();
        user.setId("user_id");
        user.setName("Test User");

        // Create lists for associations
        List<Comment> commentList = new ArrayList<>();
        List<Scrap> scrapList = new ArrayList<>();
        List<PostFile> postFileList = new ArrayList<>();

        // Create a new post instance
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Test Post");
        post.setContent("Test content");
        post.setType(1);
        post.setUser(user);
        post.setCommentList(commentList);
        post.setScrapList(scrapList);
        post.setPostFileList(postFileList);

        // Validate entity fields via getters
        assertEquals(1L, post.getId());
        assertEquals("Test Post", post.getTitle());
        assertEquals("Test content", post.getContent());
        assertEquals(1, post.getType());
        assertEquals(user, post.getUser());
        assertEquals(commentList, post.getCommentList());
        assertEquals(scrapList, post.getScrapList());
        assertEquals(postFileList, post.getPostFileList());

        // Validate entity builder
        Post postFromBuilder = Post.builder()
                .title("Builder Post")
                .content("Builder content")
                .type(2)
                .user(user)
                .commentList(commentList)
                .scrapList(scrapList)
                .postFileList(postFileList)
                .build();

        assertEquals("Builder Post", postFromBuilder.getTitle());
        assertEquals("Builder content", postFromBuilder.getContent());
        assertEquals(2, postFromBuilder.getType());
        assertEquals(user, postFromBuilder.getUser());
        assertEquals(commentList, postFromBuilder.getCommentList());
        assertEquals(scrapList, postFromBuilder.getScrapList());
        assertEquals(postFileList, postFromBuilder.getPostFileList());
    }

}
