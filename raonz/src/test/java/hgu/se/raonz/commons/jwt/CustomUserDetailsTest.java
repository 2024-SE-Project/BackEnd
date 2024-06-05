package hgu.se.raonz.commons.jwt;


import hgu.se.raonz.user.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CustomUserDetailsTest {

    private User user;

    @BeforeEach
    void setUp() {
        // Create a sample User instance for testing

        user = new User();
        user.setName("testuser");

    }

    @Test
    void testGetUsername() {
        CustomUserDetails userDetails = new CustomUserDetails(user);

        String username = userDetails.getUsername();

        assertEquals("testuser", username);
    }

}
