package hgu.se.raonz.post.presentation.controller;

import hgu.se.raonz.post.application.service.PostService;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.post.presentation.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin("*")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @PostMapping("/post/add")
    public ResponseEntity<String> addPost(@RequestBody PostRequest postRequest) {
        Post post = postService.addPost(postRequest, 1);
        post.setType(1);

        return ResponseEntity.ok(""+post.getPostId());
    }

//    @PostMapping("/post/delete/{postId}")
//    public ResponseEntity<String> deletePost(@PathVariable String postId) {
//        Post post = postService.deletePost(Long.parseLong(postId));
//
//
//
//
//        return ResponseEntity.ok(postId);
//    }
}
