package hgu.se.raonz.user.application.service;


import hgu.se.raonz.commons.security.Authority;
import hgu.se.raonz.post.domain.entity.Post;
import hgu.se.raonz.post.presentation.response.PostResponse;
import hgu.se.raonz.postLike.application.dto.PostLikeDto;
import hgu.se.raonz.postLike.domain.entity.PostLike;
import hgu.se.raonz.postLike.domain.repository.PostLikeRepository;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.domain.entity.Scrap;
import hgu.se.raonz.scrap.domain.repository.ScrapRepository;
import hgu.se.raonz.user.application.dto.UserDto;
import hgu.se.raonz.user.application.dto.UserInfoDto;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import hgu.se.raonz.user.presentation.request.UserRequest;
import hgu.se.raonz.user.presentation.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ScrapRepository scrapRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public User addUser(UserRequest userRequest) {
        User user = User.toAdd(userRequest);

        user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
        userRepository.save(user);

        return user;
    }

    @Transactional
    public String updateUser(String userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        System.out.println("Success to find User");
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setStudentId(userRequest.getStudentId());
        return userId;
    }

    @Transactional
    public UserDto getUserDto(String userId) {
        User user = userRepository.findUserByUserId(userId);
        if (user == null) return null;

        System.out.println("Success to find User");
        return UserDto.toResponse(user);
    }

    @Transactional
    public UserInfoDto getUserInfoDto(String userId, List<ScrapDto> scrapDtoList, List<PostLikeDto> postLikeDtoList, List<PostResponse> postResponseList) {
        User user = userRepository.findById(userId).orElse(null);
        List<PostLike> postLikeList = postLikeRepository.findPostLikeByUserId(userId);

        if (user == null) return null;
        System.out.println("Success to find User");
        return UserInfoDto.toResponse(user, scrapDtoList, postLikeDtoList, postResponseList);
    }

    @Transactional
    public User loadUserByUserId(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public User updateRC(String RC, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        user.setRC(RC);
        userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateName(String value, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        user.setName(value);
        userRepository.save(user);

        return user;
    }

    @Transactional
    public User updateEmail(String value, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        user.setEmail(value);
        userRepository.save(user);

        return user;
    }

    @Transactional
    public User updatePhone(String value, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        user.setPhoneNumber(value);
        userRepository.save(user);

        return user;
    }
}
