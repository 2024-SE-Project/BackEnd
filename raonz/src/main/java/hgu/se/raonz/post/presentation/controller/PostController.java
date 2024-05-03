package hgu.se.raonz.post.presentation.controller;

import hgu.se.raonz.post.application.service.PostService;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin("*")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @PostMapping("/post/add")
    public ResponseEntity<Long> addPost(@RequestBody PostRequest postRequest) {
        Post post = postService.addPost(postRequest, 1);
        post.setType(1);

        return ResponseEntity.ok(post.getPostId());
    }

    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<Long> deletePost(@PathVariable Long postId) {
        Long returnId = postService.deletePost(postId);

        return ResponseEntity.ok(returnId);
    }

    @PatchMapping("/post/update/{postId}")
    public ResponseEntity<Long> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        Long returnId = postService.updatePost(postId, postUpdateRequest);

        return ResponseEntity.ok(returnId);
    }
}
