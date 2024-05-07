package hgu.se.raonz.scrap.application.service;

import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.domain.repository.PostRepository;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.scrap.domain.repository.ScrapRepository;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addScrap(Long postId, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        if(user != null && post != null) {
            Scrap scrap = scrapRepository.save(Scrap.toAdd(user, post));

            return scrap.getId();
        }

        return null;

    }

    @Transactional
    public Long deleteScrap(Long scrapId, String userId) {
        Scrap scrap = scrapRepository.findById(scrapId).orElse(null);

        if(scrap == null) {
            return null;
        }

        if(scrap.getUser().getId().equals(userId)) {
            scrapRepository.delete(scrap);
            return scrapId;
        }

        return null;
    }

    @Transactional
    public List<ScrapDto> getAllScrap(String userId) {
        List<Scrap> scrapList = scrapRepository.findScrapByUserId(userId);

        return scrapList.stream().map(ScrapDto::toResponse).toList();
    }
}
