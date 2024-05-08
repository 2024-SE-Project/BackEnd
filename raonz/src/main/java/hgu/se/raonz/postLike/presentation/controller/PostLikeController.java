package hgu.se.raonz.postLike.presentation.controller;

import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.postLike.application.dto.PostLikeDto;
import hgu.se.raonz.postLike.application.service.PostLikeService;
import hgu.se.raonz.postLike.presentation.response.PostLikeResponse;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.presentation.response.ScrapResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostLikeController {
    private final JWTProvider jwtProvider;
    private final PostLikeService postLikeService;

    @PostMapping("/like/add/{pid}")
    public ResponseEntity<Long> addPostLike(@PathVariable Long pid, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long scrapId = postLikeService.addPostLike(pid, uid);

        return ResponseEntity.ok(scrapId);
    }

    @DeleteMapping("/like/delete/{postLikId}")
    public ResponseEntity<Long> deletePostLike(@PathVariable Long postLikId, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long returnId = postLikeService.deletePostLike(postLikId, uid);

        return ResponseEntity.ok(returnId);
    }

    @GetMapping("/like/get")
    public ResponseEntity<List<PostLikeResponse>> getAllPostLike(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        List<PostLikeDto> postLikeDtoList = postLikeService.getAllScrap(uid);
        List<PostLikeResponse> postLikeResponseList = postLikeDtoList.stream().map(PostLikeResponse::toResponse).toList();

        return ResponseEntity.ok(postLikeResponseList);
    }
}
