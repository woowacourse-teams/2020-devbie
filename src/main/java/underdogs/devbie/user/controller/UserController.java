package underdogs.devbie.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import underdogs.devbie.auth.controller.resolver.LoginUser;
import underdogs.devbie.user.domain.User;

@RestController
public class UserController {

    @GetMapping("/api/test")
    public String test(@LoginUser User user) {
        return user.getEmail();
    }
}
