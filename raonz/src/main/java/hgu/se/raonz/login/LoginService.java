package hgu.se.raonz.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import hgu.se.raonz.commons.jwt.JWTProvider;
import hgu.se.raonz.login.response.GoogleResponse;
import hgu.se.raonz.login.social.GoogleOAuthToken;
import hgu.se.raonz.login.social.GoogleOauth;
import hgu.se.raonz.login.social.GoogleUser;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final GoogleOauth googleOauth;
    private final HttpServletResponse response;
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
}

// @Value("${spring.security.oauth2.client.registration.google.redirecct_url}")

// https://accounts.google.com/o/oauth2/v2/auth?client_id=783995798085-4spraevn1eimivgblqhj2t0usnm9e9u7.apps.googleusercontent.com&redirect_uri=http://localhost:8080/api/v1/oauth2/google&response_type=code&scope=email%20profile&access-type=offline

//             client-id: 783995798085-4spraevn1eimivgblqhj2t0usnm9e9u7.apps.googleusercontent.com
//            client-secret: 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