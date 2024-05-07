package hgu.se.raonz.user.presentation.controller;

import hgu.se.raonz.user.application.service.UserService;
import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.presentation.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest) {

        User user = userService.addUser(userRequest);

        return ResponseEntity.ok(user.getId());
    }

    @GetMapping("/user")
    public ResponseEntity<Integer> test() {

        Integer num = 1;
        return ResponseEntity.ok(num);
    }
}
