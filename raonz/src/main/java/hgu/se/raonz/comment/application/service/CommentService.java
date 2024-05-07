package hgu.se.raonz.comment.application.service;


import hgu.se.raonz.comment.domain.entity.Comment;
import hgu.se.raonz.comment.domain.repository.CommentRepository;
import hgu.se.raonz.comment.presentation.request.CommentRequest;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long toAdd(Long pid, String uid, CommentRequest commentRequest) {
        Post post = postRepository.findById(pid).orElse(null);
        User user = userRepository.findById(uid).orElse(null);

        if(post != null && user != null) {
            Comment comment = commentRepository.save(Comment.toAdd(post, user, commentRequest));

            return comment.getId();
        }

        return null;
    }

    @Transactional
    public Long toDelete(Long commentId, String uid) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if(comment == null) {
            return null;
        }

        if(comment.getUser().getId().equals(uid)) {
            commentRepository.delete(comment);
            return commentId;
        }

        return null;
    }

    @Transactional
    public Long toUpdate(Long commentId, String uid, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if(comment == null) {
            return null;
        }

        if(comment.getUser().getId().equals(uid)) {
            comment.setContents(commentRequest.getContents());

            commentRepository.save(comment);

            return comment.getId();
        }

        return null;
    }
}








