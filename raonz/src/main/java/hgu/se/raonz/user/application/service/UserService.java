package hgu.se.raonz.user.application.service;


import hgu.se.raonz.user.domain.entity.User;
import hgu.se.raonz.user.domain.repository.UserRepository;
import hgu.se.raonz.user.presentation.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long addUser(UserRequest userRequest) {
        User user = userRepository.save(User.toAdd(userRequest));

        return user.getUserId();
    }
}
