package hgu.se.raonz.post.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;
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

    @Transactional
    public Post addPost(PostRequest postRequest, int type, String userId) {
        List<User> userList = userRepository.findUserListByUserId(userId);
        if (userList.size() != 1) {
            System.out.println(userId);
            System.out.println("userList size = " + userList.size() + "Duplicate email");
//            return null;
        }
        Post post = Post.toAdd(postRequest, type, userList.get(0));

        postRepository.save(post);

        return post;
    }

    @Transactional
    public Long deletePost(Long postId, String userId) {
        Post post = postRepository.findById(postId).orElse(null);
        List<User> userList = userRepository.findUserListByUserId(userId);

        if(post != null) {
            if (!post.getUser().getId().equals(userId)) { // 나중에 팀 클래스 생기면 수정 필요
                System.out.println(userId);
                return null;
            }
            Long returnId = post.getId();
            postRepository.delete(post);

            return returnId;
        }

        return null;
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
    public PostResponse getPostResponse(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) return null;
        System.out.println("Success to find Post");
        return PostResponse.toResponse(post);
    }

    @Transactional
    public List<PostResponse> getPostResponseList(int index, int type) {
        index--; // index == pageNum-1
        List<Post> postList = postRepository.findPostListByType(type);
        postList.sort(Comparator.comparing(Post::getId).reversed());
        List<PostResponse> postResponseList = new ArrayList<>();
        for (int i = index*10; i < index*10 + 10 && i < postList.size(); i++) {
            postResponseList.add(PostResponse.toResponse(postList.get(i)));
        }
        return postResponseList;
    }
}
