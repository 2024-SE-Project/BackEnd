package hgu.se.raonz.user.presentation.controller;

import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.post.application.dto.PostDto;
import hgu.se.raonz.post.application.service.PostService;
import hgu.se.raonz.post.presentation.response.PostResponse;
import hgu.se.raonz.postLike.application.dto.PostLikeDto;
import hgu.se.raonz.postLike.application.service.PostLikeService;
import hgu.se.raonz.scrap.application.dto.ScrapDto;
import hgu.se.raonz.scrap.application.service.ScrapService;
import hgu.se.raonz.user.application.dto.UserDto;
import hgu.se.raonz.user.application.dto.UserInfoDto;
import hgu.se.raonz.user.application.service.UserService;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.presentation.request.UserRCUpdate;
import hgu.se.raonz.user.presentation.request.UserRequest;
import hgu.se.raonz.user.presentation.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ScrapService scrapService;
    private final PostLikeService postLikeService;
    private final PostService postService;
    private final JWTProvider jwtProvider;

    @PostMapping("/")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {

        User user = userService.addUser(userRequest);

        return ResponseEntity.ok(user.getId());
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserInfoDto> getUserInfo(HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String userId = jwtProvider.getAccount(token);
        List<ScrapDto> scrapDtoList = scrapService.getAllScrap(userId);
        List<PostLikeDto> postLikeDtoList = postLikeService.getAllPostLike(userId);
        List<PostResponse> postResponseList = postService.getPostResponseWithUserId(userId);
        UserInfoDto userInfoDto = userService.getUserInfoDto(userId, scrapDtoList, postLikeDtoList, postResponseList);


        return ResponseEntity.ok(userInfoDto);
    }

    @PatchMapping("/mypage/update")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String userId = jwtProvider.getAccount(token);
        String resultId = userService.updateUser(userId, userRequest);

        return ResponseEntity.ok(resultId);
    }

    @GetMapping("/user")
    public ResponseEntity<Integer> test() {

        Integer num = 1;
        return ResponseEntity.ok(num);
    }

    @PatchMapping("/mypage/rc/update")
    public ResponseEntity<String> updateUserRc(@RequestBody UserRCUpdate userRCUpdate, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String userId = jwtProvider.getAccount(token);
        User user = userService.updateRC(userRCUpdate.getValue(), userId);
        System.out.println(user.getId());

        return ResponseEntity.ok(user.getId());
    }

    @PatchMapping("/mypage/name/update")
    public ResponseEntity<String> updateUserName(@RequestBody UserRCUpdate userRCUpdate, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String userId = jwtProvider.getAccount(token);
        User user = userService.updateName(userRCUpdate.getValue(), userId);

        return ResponseEntity.ok(user.getId());
    }

    @PatchMapping("/mypage/email/update")
    public ResponseEntity<String> updateUserEmail(@RequestBody UserRCUpdate userRCUpdate, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String userId = jwtProvider.getAccount(token);
        User user = userService.updateEmail(userRCUpdate.getValue(), userId);

        return ResponseEntity.ok(user.getId());
    }

    @PatchMapping("/mypage/phone/update")
    public ResponseEntity<String> updateUserPhone(@RequestBody UserRCUpdate userRCUpdate, HttpServletRequest request) {
        String token = jwtProvider.resolveToken(request);
        String userId = jwtProvider.getAccount(token);
        User user = userService.updatePhone(userRCUpdate.getValue(), userId);

        return ResponseEntity.ok(user.getId());
    }
}
