package hgu.se.raonz.comment.presentation.controller;


import hgu.se.raonz.comment.application.service.CommentService;
import hgu.se.raonz.comment.presentation.request.CommentRequest;
import hgu.se.raonz.commons.jwt.JWTProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class CommentController {
    private final CommentService commentService;
    private final JWTProvider jwtProvider;

    @PostMapping("/comment/add/{pid}")
    public ResponseEntity<Long> toAdd(@PathVariable Long pid, @RequestBody CommentRequest commentRequest, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long commentId = commentService.toAdd(pid, uid, commentRequest);

        return ResponseEntity.ok(commentId);
    }

    @DeleteMapping("/comment/delete/{cid}")
    public ResponseEntity<Long> toDelete(@PathVariable Long cid, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long commentId = commentService.toDelete(cid, uid);

        return ResponseEntity.ok(commentId);
    }

    @PatchMapping("/comment/update/{cid}")
    public ResponseEntity<Long> toUpdate(@PathVariable Long cid, HttpServletRequest request, @RequestBody CommentRequest commentRequest) {
        String token = jwtProvider.resolveToken(request);
        String uid = jwtProvider.getAccount(token);

        Long commentId = commentService.toUpdate(cid, uid, commentRequest);

        return ResponseEntity.ok(commentId);
    }

}
