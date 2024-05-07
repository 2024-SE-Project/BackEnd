package hgu.se.raonz.commentlike.application.service;


import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.comment.domain.repository.CommentRepository;
import hgu.se.raonz.commentlike.domain.entity.CommentLike;
import hgu.se.raonz.commentlike.domain.repository.CommentLikeRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long addLike(Long commentId, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        Comment comment = commentRepository.findById(commentId).orElse(null);

        CommentLike commentLike = commentLikeRepository.findCommentLikeByUserIdAndCommentId(userId, commentId);

        if(commentLike != null) {
            return null;
        }

        if(user != null && comment != null) {
            commentLike = commentLikeRepository.save(CommentLike.toAdd(user, comment));

            return commentLike.getId();
        }

        return null;
    }

    @Transactional
    public Long removeLike(Long commentId, String userId) {
        CommentLike commentLike = commentLikeRepository.findCommentLikeByUserIdAndCommentId(userId, commentId);

        if(commentLike == null) {
            return null;
        }

        commentLikeRepository.delete(commentLike);

        return commentLike.getId();
    }
}
