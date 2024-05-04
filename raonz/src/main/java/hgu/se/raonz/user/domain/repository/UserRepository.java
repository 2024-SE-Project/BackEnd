package hgu.se.raonz.user.domain.repository;

import hgu.se.raonz.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


}
