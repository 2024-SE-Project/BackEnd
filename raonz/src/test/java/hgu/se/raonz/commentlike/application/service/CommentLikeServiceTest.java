package hgu.se.raonz.commentlike.application.service;

import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.comment.domain.repository.CommentRepository;
import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.commentlike.domain.repository.CommentLikeRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CommentLikeServiceTest {

    @Mock
    private CommentLikeRepository commentLikeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentLikeService commentLikeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddLike_Success() {
        Long commentId = 1L;
        String userId = "user1";

        User user = new User();
        user.setId(userId);

        Comment comment = new Comment();
        comment.setId(commentId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentLikeRepository.findCommentLikeByUserIdAndCommentId(userId, commentId)).thenReturn(null);
        when(commentLikeRepository.save(any(CommentLike.class))).thenAnswer(invocation -> {
            CommentLike savedCommentLike = invocation.getArgument(0);
            savedCommentLike.setId(1L); // Simulating saving and setting ID
            return savedCommentLike;
        });

        Long likeId = commentLikeService.addLike(commentId, userId);

        assertNotNull(likeId);
        assertEquals(1L, likeId); // Assuming like ID returned is 1
    }

    @Test
    void testAddLike_AlreadyLiked() {
        Long commentId = 1L;
        String userId = "user1";

        User user = new User();
        user.setId(userId);

        Comment comment = new Comment();
        comment.setId(commentId);

        CommentLike existingCommentLike = new CommentLike();
        existingCommentLike.setId(1L);
        existingCommentLike.setUser(user);
        existingCommentLike.setComment(comment);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentLikeRepository.findCommentLikeByUserIdAndCommentId(userId, commentId)).thenReturn(existingCommentLike);

        Long likeId = commentLikeService.addLike(commentId, userId);

        assertNull(likeId); // Should return null because already liked
    }

    @Test
    void testAddLike_UserNotFound() {
        Long commentId = 1L;
        String userId = "user1";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Long likeId = commentLikeService.addLike(commentId, userId);

        assertNull(likeId); // User not found, should return null
    }

    @Test
    void testAddLike_CommentNotFound() {
        Long commentId = 1L;
        String userId = "user1";

        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        Long likeId = commentLikeService.addLike(commentId, userId);

        assertNull(likeId); // Comment not found, should return null
    }

    @Test
    void testRemoveLike_Success() {
        Long commentId = 1L;
        String userId = "user1";

        CommentLike existingCommentLike = new CommentLike();
        existingCommentLike.setId(1L);

        when(commentLikeRepository.findCommentLikeByUserIdAndCommentId(userId, commentId)).thenReturn(existingCommentLike);

        Long removedLikeId = commentLikeService.removeLike(commentId, userId);

        assertNotNull(removedLikeId);
        assertEquals(1L, removedLikeId); // Assuming removed like ID is 1
        verify(commentLikeRepository, times(1)).delete(existingCommentLike);
    }

    @Test
    void testRemoveLike_NotFound() {
        Long commentId = 1L;
        String userId = "user1";

        when(commentLikeRepository.findCommentLikeByUserIdAndCommentId(userId, commentId)).thenReturn(null);

        Long removedLikeId = commentLikeService.removeLike(commentId, userId);

        assertNull(removedLikeId); // Should return null because like not found
        verify(commentLikeRepository, never()).delete(any(CommentLike.class));
    }
}
