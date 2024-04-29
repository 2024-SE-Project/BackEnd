package hgu.se.raonz.user.presentation.controller;

import hgu.se.raonz.user.application.service.UserService;
import hgu.se.raonz.user.presentation.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("user/add")
    public ResponseEntity<Long> addUser(@RequestBody UserRequest userRequest) {
        Long userId = userService.addUser(userRequest);

        return ResponseEntity.ok(userId);
    }
}
