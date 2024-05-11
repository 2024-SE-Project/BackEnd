package hgu.se.raonz.commentlike.presentation.controller;

import hgu.se.raonz.commentlike.application.service.CommentLikeService;
import hgu.se.raonz.commons.jwt.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class    CommentLikeController {
    private final CommentLikeService commentLikeService;
    private final JWTProvider jwtProvider;

    @PostMapping("/like/comment/{cid}")
    public ResponseEntity<Long> addLike(@PathVariable Long cid, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long likeId = commentLikeService.addLike(cid, uid);

        return ResponseEntity.ok(likeId);
    }

    @DeleteMapping("/like/comment/{cid}")
    public ResponseEntity<Long> deleteLike(@PathVariable Long cid, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long likeId = commentLikeService.removeLike(cid, uid);

        return ResponseEntity.ok(likeId);
    }
}














