package hgu.se.raonz.post.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;
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

    @Transactional
    public Post addPost(PostRequest postRequest, int type) {
        Post post = Post.toAdd(postRequest, type);

        postRepository.save(post);

        return post;
    }

    @Transactional
    public Long deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);

        if(post != null) {
            Long returnId = post.getPostId();
            postRepository.delete(post);

            return returnId;
        }

        return null;
    }

    @Transactional
    public Long updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId).orElse(null);

        if(post != null) {
            post.setTitle(postUpdateRequest.getTitle());
            post.setContent(postUpdateRequest.getContent());
            postRepository.save(post);

            return post.getPostId();
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
        postList.sort(Comparator.comparing(Post::getPostId).reversed());
        List<PostResponse> postResponseList = new ArrayList<>();
        for (int i = index*10; i < index*10 + 10 && i < postList.size(); i++) {
            postResponseList.add(PostResponse.toResponse(postList.get(i)));
        }
        return postResponseList;
    }
}
