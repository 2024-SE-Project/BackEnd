package hgu.se.raonz.postLike.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.postLike.application.dto.PostLikeDto;
import hgu.se.raonz.postLike.domain.entity.PostLike;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.postLike.domain.repository.PostLikeRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addPostLike(Long postId, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        if(user == null || post == null) return null;
        if(postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId) != null) return null;
        PostLike postLike = postLikeRepository.save(PostLike.toAdd(user, post));
        return postLike.getId();
    }

    @Transactional
    public Long deletePostLike(Long postLikeId, String userId) {
        PostLike postLike = postLikeRepository.findById(postLikeId).orElse(null);

        if(postLike == null) {
            return null;
        }

        if(postLike.getUser().getId().equals(userId)) {
            postLikeRepository.delete(postLike);
            return postLikeId;
        }

        return null;
    }

    @Transactional
    public List<PostLikeDto> getAllPostLike(String userId) {
        List<PostLike> postLikeList = postLikeRepository.findPostLikeByUserId(userId);

        return postLikeList.stream().map(PostLikeDto::toResponse).toList();
    }
    @Transactional
    public boolean isPostLike(String userId, Long postId) {
        return postLikeRepository.findPostLikeByUserIdAndPostId(userId, postId) != null;
    }
}
