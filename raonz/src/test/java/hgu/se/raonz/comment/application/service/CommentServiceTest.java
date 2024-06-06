package hgu.se.raonz.comment.application.service;

import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.comment.domain.repository.CommentRepository;
import hgu.se.raonz.comment.presentation.request.CommentRequest;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
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

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToAddComment_Success() {
        Long postId = 1L;
        String userId = "user1";
        CommentRequest commentRequest = new CommentRequest();

        Post post = new Post();
        post.setId(postId);

        User user = new User();
        user.setId(userId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setId(1L); // Simulating saving and setting ID
            return savedComment;
        });

        Long commentId = commentService.toAdd(postId, userId, commentRequest);

        assertNotNull(commentId);
        assertEquals(1L, commentId); // Assuming comment ID returned is 1
    }

    @Test
    void testToAddComment_PostNotFound() {
        Long postId = 1L;
        String userId = "user1";
        CommentRequest commentRequest = new CommentRequest();

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Long commentId = commentService.toAdd(postId, userId, commentRequest);

        assertNull(commentId);
    }

    @Test
    void testToAddComment_UserNotFound() {
        Long postId = 1L;
        String userId = "user1";
        CommentRequest commentRequest = new CommentRequest();

        Post post = new Post();
        post.setId(postId);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Long commentId = commentService.toAdd(postId, userId, commentRequest);

        assertNull(commentId);
    }

    @Test
    void testToDeleteComment_Success() {
        Long commentId = 1L;
        String userId = "user1";

        Comment comment = new Comment();
        comment.setId(commentId);
        User user = new User();
        user.setId(userId);
        comment.setUser(user);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Long deletedCommentId = commentService.toDelete(commentId, userId);

        assertNotNull(deletedCommentId);
        assertEquals(commentId, deletedCommentId);
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void testToDeleteComment_CommentNotFound() {
        Long commentId = 1L;
        String userId = "user1";

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        Long deletedCommentId = commentService.toDelete(commentId, userId);

        assertNull(deletedCommentId);
        verify(commentRepository, never()).delete(any(Comment.class));
    }

    @Test
    void testToDeleteComment_UnauthorizedUser() {
        Long commentId = 1L;
        String userId = "user1";

        Comment comment = new Comment();
        comment.setId(commentId);
        User user = new User();
        user.setId("differentUser"); // Not the same user
        comment.setUser(user);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Long deletedCommentId = commentService.toDelete(commentId, userId);

        assertNull(deletedCommentId);
        verify(commentRepository, never()).delete(any(Comment.class));
    }

    @Test
    void testToUpdateComment_Success() {
        Long commentId = 1L;
        String userId = "user1";
        CommentRequest updatedCommentRequest = new CommentRequest();

        Comment comment = new Comment();
        comment.setId(commentId);
        User user = new User();
        user.setId(userId);
        comment.setUser(user);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Long updatedCommentId = commentService.toUpdate(commentId, userId, updatedCommentRequest);

        assertNotNull(updatedCommentId);
        assertEquals(commentId, updatedCommentId);
        assertEquals(updatedCommentRequest.getContents(), comment.getContents());
    }

    @Test
    void testToUpdateComment_CommentNotFound() {
        Long commentId = 1L;
        String userId = "user1";
        CommentRequest updatedCommentRequest = new CommentRequest();

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        Long updatedCommentId = commentService.toUpdate(commentId, userId, updatedCommentRequest);

        assertNull(updatedCommentId);
    }

    @Test
    void testToUpdateComment_UnauthorizedUser() {
        Long commentId = 1L;
        String userId = "user1";
        CommentRequest updatedCommentRequest = new CommentRequest();

        Comment comment = new Comment();
        comment.setId(commentId);
        User user = new User();
        user.setId("differentUser"); // Not the same user
        comment.setUser(user);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Long updatedCommentId = commentService.toUpdate(commentId, userId, updatedCommentRequest);

        assertNull(updatedCommentId);
        verify(commentRepository, never()).save(any(Comment.class));
    }
}
