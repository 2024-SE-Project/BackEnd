package hgu.se.raonz.post.presentation.controller;

import hgu.se.raonz.post.application.service.PostService;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

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

    @DeleteMapping("/post/delete/{postId}") // 삭제
    public ResponseEntity<Long> deletePost(@PathVariable Long postId) {
        Long returnId = postService.deletePost(postId);

        return ResponseEntity.ok(returnId);
    }

    @PatchMapping("/post/update/{postId}")
    public ResponseEntity<Long> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        Long returnId = postService.updatePost(postId, postUpdateRequest);

        return ResponseEntity.ok(returnId);
    }

    @GetMapping("/post/get/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse postResponse = postService.getPostResponse(id);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/post/get/all/{index}")
    public ResponseEntity<List<PostResponse>> getPostAll(@PathVariable int index) {
        // Return the top 10 posts with the highest postId among posts with 1 post type in the order of highest order
        List<PostResponse> postResponseList = postService.getPostResponseList(index, 1);

        return ResponseEntity.ok(postResponseList);
    }
}
