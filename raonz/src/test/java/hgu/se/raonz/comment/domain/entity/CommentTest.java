package hgu.se.raonz.comment.domain.entity;

import hgu.se.raonz.comment.presentation.request.CommentRequest;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.user.domain.entity.User;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentTest {

    @Test
    void testCommentEntityCreation() {
        // Create test data
        Long commentId = 1L;
        User user = new User();
        user.setId("user1");
        Post post = new Post();
        post.setId(1L);

        CommentRequest commentRequest = new CommentRequest();

        // Create a new comment using the static factory method
        Comment comment = Comment.toAdd(post, user, commentRequest);
        comment.setId(commentId);

        // Assert that the comment entity is created correctly
        assertNotNull(comment);
        assertEquals(commentId, comment.getId());
        assertEquals(user, comment.getUser());
        assertEquals(post, comment.getPost());
    }

    @Test
    void testCommentBuilder() {
        // Create test data
        Long commentId = 2L;
        String contents = "Another test comment";
        User user = new User();
        user.setId("user2");
        Post post = new Post();
        post.setId(2L);

        // Using the builder directly
        Comment comment = Comment.builder()
                .id(commentId)
                .user(user)
                .post(post)
                .contents(contents)
                .build();

        // Assert that the comment is built correctly
        assertNotNull(comment);
        assertEquals(commentId, comment.getId());
        assertEquals(user, comment.getUser());
        assertEquals(post, comment.getPost());
    }


}
