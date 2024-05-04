package hgu.se.raonz.login;

import hgu.se.raonz.login.social.GoogleUser;
import hgu.se.raonz.user.presentation.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login/oauth2/google") //GOOGLE이 들어올 것이다.
    public void socialLoginRedirect() throws IOException {
        loginService.request();
    }

    @ResponseBody
    @GetMapping(value = "api/v1/oauth2/google") // 여기서 로그인 및 회원가입 여부 확인을 해야함
    public ResponseEntity<UserResponse> callback (@RequestParam(value = "code") String code) throws IOException {
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);

        GoogleUser googleResponse = loginService.googleLogin(code);

        UserResponse userResponse = loginService.login(googleResponse);

        return ResponseEntity.ok(userResponse);
    }

    
}