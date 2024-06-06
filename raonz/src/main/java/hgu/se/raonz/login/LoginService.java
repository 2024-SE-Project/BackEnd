package hgu.se.raonz.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.commons.security.Authority;
import hgu.se.raonz.login.social.GoogleOAuthToken;
import hgu.se.raonz.login.social.GoogleOauth;
import hgu.se.raonz.login.social.GoogleUser;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import hgu.se.raonz.user.presentation.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    public void request() throws IOException {
        String redirectURL = googleOauth.getOauthRedirectURL();

        response.sendRedirect(redirectURL);
    }

    @Transactional
    public GoogleUser googleLogin(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse= googleOauth.requestAccessToken(code);
        //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
        GoogleOAuthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);
        //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

        return googleUser;
    }

    @Transactional
    public UserResponse login(GoogleUser googleUser) {
        Optional<User> optionalUser = userRepository.findById(googleUser.getId());
        User user;

        if(optionalUser.isEmpty()) {
            user = User.builder()
                    .id(googleUser.getId())
                    .name(googleUser.getName())
                    .email(googleUser.getEmail())
                    .RC("RC를 수정해주세요.")
                    .phoneNumber("핸드폰 번호를 수정해주세요.")
                    .build();
            user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
            userRepository.save(user);
        } else {
            user = optionalUser.get();
        }

        return UserResponse.toResponse(user, jwtProvider.createToken(user.getId(), user.getRoles()));
    }
}
