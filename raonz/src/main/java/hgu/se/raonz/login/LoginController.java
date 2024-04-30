package hgu.se.raonz.login;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {

    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/code/google/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        System.out.println("!@#!@#!@#!@#");
        loginService.socialLogin(code, registrationId);
    }
}