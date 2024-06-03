package hgu.se.raonz.login;

import hgu.se.raonz.login.social.GoogleUser;
import hgu.se.raonz.user.presentation.response.UserResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {
    private final LoginService loginService;
    @CrossOrigin("*")
    @PostMapping("/login/oauth2/google") //GOOGLE이 들어올 것이다.
    public void socialLoginRedirect() throws IOException {
        loginService.request();
    }

    @ResponseBody
    @CrossOrigin("*")
    @GetMapping(value = "/api/v1/oauth2/google") // 여기서 로그인 및 회원가입 여부 확인을 해야함
    public void callback (@RequestParam(value = "code") String code, HttpServletResponse response) throws IOException {

        GoogleUser googleResponse = loginService.googleLogin(code);

        UserResponse userResponse = loginService.login(googleResponse);

        String userId = URLEncoder.encode(userResponse.getUserId(), StandardCharsets.UTF_8.toString());
        String userName = URLEncoder.encode(userResponse.getName(), StandardCharsets.UTF_8.toString());
        String token = URLEncoder.encode(userResponse.getToken(), StandardCharsets.UTF_8.toString());


        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:3000/dashboard/main")
                .queryParam("userId", userId)
                .queryParam("userName", userName)
                .queryParam("token", token)
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }

    
}

