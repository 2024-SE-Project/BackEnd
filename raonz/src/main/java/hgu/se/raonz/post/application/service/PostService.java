package hgu.se.raonz.post.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;
import hgu.se.raonz.postLike.domain.repository.PostLikeRepository;
import hgu.se.raonz.scrap.domain.repository.ScrapRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final ScrapRepository scrapRepository;

    @Transactional
    public Post addPost(PostRequest postRequest, int type, String userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) {
            System.out.println(userId);
            System.out.println("user is null");
            return null;
        }
        Post post = Post.toAdd(postRequest, type, user);

        postRepository.save(post);

        return post;
    }

    @Transactional
    public Long deletePost(Long postId, String userId) {
        Post post = postRepository.findById(postId).orElse(null);

        if (post == null) return null;
        if (!post.getUser().getId().equals(userId)) {
            System.out.println(userId);
            return null;
        }
        Long returnId = post.getId();
        postRepository.delete(post);

        return returnId;
    }

    @Transactional
    public Long updatePost(Long postId, PostUpdateRequest postUpdateRequest, String userId) {
        Post post = postRepository.findById(postId).orElse(null);

        if(post != null) {
            if (!post.getUser().getId().equals(userId)) { // 나중에 팀 클래스 생기면 수정 필요
                System.out.println(userId);
                return null;
            }
            post.setTitle(postUpdateRequest.getTitle());
            post.setContent(postUpdateRequest.getContent());
            postRepository.save(post);

            return post.getId();
        }

        return null;
    }

    @Transactional
    public PostResponse getPostResponse(Long postId, String userId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return null;
        System.out.println("Success to find Post");
        boolean isPostLike = postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId) != null;
        boolean isScrap = scrapRepository.findScrapByUserIdAndPostId(userId, postId) != null;
        return PostResponse.toResponse(post, isPostLike, isScrap);
    }

    @Transactional
    public List<PostResponse> getPostResponseList(int index, int type, String userId) {
        index--; // index == pageNum-1
        List<Post> postList = postRepository.findPostListByType(type);
        postList.sort(Comparator.comparing(Post::getId).reversed());
        List<PostResponse> postResponseList = new ArrayList<>();
        for (int i = index*10; i < index*10 + 10 && i < postList.size(); i++) {
            Long postId = postList.get(i).getId();
            boolean isPostLike = postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId) != null;
            boolean isScrap = scrapRepository.findScrapByUserIdAndPostId(userId, postId) != null;
            postResponseList.add(PostResponse.toResponse(postList.get(i), isPostLike, isScrap));
        }
        return postResponseList;
    }
}
