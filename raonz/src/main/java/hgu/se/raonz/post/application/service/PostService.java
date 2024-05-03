package hgu.se.raonz.post.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
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

//    @Transactional
//    public Post deletePost(Long postId) {
//        Post post = postRepository.findPostByPostID(postId);
////        post.getUser().deletePost(post);
//        postRepository.delete(post);
//
//        return post;
//    }
}
