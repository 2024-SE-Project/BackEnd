package hgu.se.raonz.scrap.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.scrap.domain.repository.ScrapRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ScrapServiceTest {

    @Mock
    private ScrapRepository scrapRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ScrapService scrapService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

     @Test
    void testAddScrap_UserNotFound() {
        // Mock data
        Long postId = 1L;
        String userId = "user1";

        // Mock repository behaviors
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call service method
        Long scrapId = scrapService.addScrap(postId, userId);

        // Verify
        assertNull(scrapId);
        verify(scrapRepository, never()).save(any());
    }

    @Test
    void testAddScrap_PostNotFound() {
        // Mock data
        Long postId = 1L;
        String userId = "user1";

        User mockUser = new User();
        mockUser.setId(userId);

        // Mock repository behaviors
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Call service method
        Long scrapId = scrapService.addScrap(postId, userId);

        // Verify
        assertNull(scrapId);
        verify(scrapRepository, never()).save(any());
    }

    @Test
    void testAddScrap_AlreadyScrapped() {
        // Mock data
        Long postId = 1L;
        String userId = "user1";

        User mockUser = new User();
        mockUser.setId(userId);

        Post mockPost = new Post();
        mockPost.setId(postId);

        Scrap existingScrap = new Scrap();
        existingScrap.setId(1L);
        existingScrap.setUser(mockUser);
        existingScrap.setPost(mockPost);

        // Mock repository behaviors
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));
        when(scrapRepository.findScrapByUserIdAndPostId(userId, postId)).thenReturn(existingScrap);

        // Call service method
        Long scrapId = scrapService.addScrap(postId, userId);

        // Verify
        assertNull(scrapId);
        verify(scrapRepository, never()).save(any());
    }
    @Test
    void testDeleteScrap_Success() {
        // Mock data
        Long scrapId = 1L;
        String userId = "user1";

        Scrap mockScrap = new Scrap();
        mockScrap.setId(scrapId);
        User mockUser = new User();
        mockUser.setId(userId);
        mockScrap.setUser(mockUser);

        // Mock repository behaviors
        when(scrapRepository.findById(scrapId)).thenReturn(Optional.of(mockScrap));

        // Call service method
        Long deletedScrapId = scrapService.deleteScrap(scrapId, userId);

        // Verify
        assertNotNull(deletedScrapId);
        assertEquals(scrapId, deletedScrapId);
        verify(scrapRepository, times(1)).delete(mockScrap);
    }

    @Test
    void testDeleteScrap_ScrapNotFound() {
        // Mock data
        Long scrapId = 1L;
        String userId = "user1";

        // Mock repository behaviors
        when(scrapRepository.findById(scrapId)).thenReturn(Optional.empty());

        // Call service method
        Long deletedScrapId = scrapService.deleteScrap(scrapId, userId);

        // Verify
        assertNull(deletedScrapId);
        verify(scrapRepository, never()).delete(any());
    }

    @Test
    void testDeleteScrap_UnauthorizedUser() {
        // Mock data
        Long scrapId = 1L;
        String userId = "user1";

        Scrap mockScrap = new Scrap();
        mockScrap.setId(scrapId);
        User mockUser = new User();
        mockUser.setId("other_user");
        mockScrap.setUser(mockUser);

        // Mock repository behaviors
        when(scrapRepository.findById(scrapId)).thenReturn(Optional.of(mockScrap));

        // Call service method
        Long deletedScrapId = scrapService.deleteScrap(scrapId, userId);

        // Verify
        assertNull(deletedScrapId);
        verify(scrapRepository, never()).delete(any());
    }


    @Test
    void testIsScrap_False() {
        // Mock data
        String userId = "user1";
        Long postId = 1L;

        // Mock repository behaviors
        when(scrapRepository.findScrapByUserIdAndPostId(userId, postId)).thenReturn(null);

        // Call service method
        boolean result = scrapService.isScrap(userId, postId);

        // Verify
        assertFalse(result);
    }



}
