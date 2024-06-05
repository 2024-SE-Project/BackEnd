package hgu.se.raonz.commentlike.domain.entity;

import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.user.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentLikeTest {

    @Test
    void testCommentLikeEntityCreation() {
        // Create test data
        Long likeId = 1L;
        User user = new User();
        user.setId("user1");
        Comment comment = new Comment();
        comment.setId(1L);

        // Create a new CommentLike using the static factory method
        CommentLike commentLike = CommentLike.toAdd(user, comment);
        commentLike.setId(likeId);

        // Assert that the CommentLike entity is created correctly
        assertNotNull(commentLike);
        assertEquals(likeId, commentLike.getId());
        assertEquals(user, commentLike.getUser());
        assertEquals(comment, commentLike.getComment());
    }

    @Test
    void testCommentLikeBuilder() {
        // Create test data
        Long likeId = 2L;
        User user = new User();
        user.setId("user2");
        Comment comment = new Comment();
        comment.setId(2L);

        // Using the builder directly
        CommentLike commentLike = CommentLike.builder()
                .id(likeId)
                .user(user)
                .comment(comment)
                .build();

        // Assert that the CommentLike is built correctly
        assertNotNull(commentLike);
        assertEquals(likeId, commentLike.getId());
        assertEquals(user, commentLike.getUser());
        assertEquals(comment, commentLike.getComment());
    }
}
