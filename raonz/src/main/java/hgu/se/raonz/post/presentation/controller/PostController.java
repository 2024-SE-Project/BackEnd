package hgu.se.raonz.post.presentation.controller;

import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.post.application.service.PostService;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.presentation.request.PostRequest;
import hgu.se.raonz.post.presentation.request.PostUpdateRequest;
import hgu.se.raonz.post.presentation.response.PostResponse;
import hgu.se.raonz.postLike.application.dto.PostLikeDto;
import hgu.se.raonz.postLike.application.service.PostLikeService;
import hgu.se.raonz.postLike.domain.entity.PostLike;
import hgu.se.raonz.scrap.application.service.ScrapService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostLikeService postLikeService;
    private final ScrapService scrapService;
    private final JWTProvider jwtProvider;

    public int getTypeByPath(String path) {
        path = path.split("/")[1].trim();
        if (path.equals("post")) return 1;
        if (path.equals("material")) return 2;
        if (path.equals("faq")) return 3;
        return -1;
    }

    @PostMapping({"/post/add", "/material/add", "/faq/add"})
    public ResponseEntity<Long> addPost(@RequestBody PostRequest postRequest, HttpServletRequest request) {
        List<String> files = postRequest.getFileList();
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);

        System.out.println(user_id);

        Post post = postService.addPost(postRequest, getTypeByPath(request.getServletPath()), user_id);

        return ResponseEntity.ok(post.getId());
    }

    @DeleteMapping({"/post/delete/{postId}", "/material/delete/{postId}", "/faq/delete/{postId}"}) // 삭제
    public ResponseEntity<Long> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);

        Long returnId = postService.deletePost(postId, user_id);

        return ResponseEntity.ok(returnId);
    }

    @PatchMapping({"/post/update/{postId}", "/material/update/{postId}", "/faq/update/{postId}"})
    public ResponseEntity<Long> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        Long returnId = postService.updatePost(postId, postUpdateRequest, user_id);

        return ResponseEntity.ok(returnId);
    }

    @GetMapping({"/post/get/{id}", "/material/get/{id}", "/faq/get/{id}"})
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        PostResponse postResponse = postService.getPostResponse(id, user_id);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping({"/post/get/all/{index}", "/material/get/all/{index}", "/faq/get/all/{index}"})
    public ResponseEntity<List<PostResponse>> getPostAll(@PathVariable int index, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String user_id = jwtProvider.getAccount(token);
        // Return the top 10 posts with the highest postId among posts with 1 post type in the order of highest order
        List<PostResponse> postResponseList = postService.getPostResponseList(index, getTypeByPath(request.getServletPath()), user_id);

        return ResponseEntity.ok(postResponseList);
    }
}
