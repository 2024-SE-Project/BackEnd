package hgu.se.raonz.post.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
