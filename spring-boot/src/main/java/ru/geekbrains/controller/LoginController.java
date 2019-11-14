package ru.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.controller.repr.UserRepr;
import ru.geekbrains.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserRepr());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserRepr userRepr,
                                  BindingResult result) {
        logger.info("New user {}", userRepr);

        if (result.hasErrors()) {
            return "register";
        }
        if (!userRepr.getPassword().equals(userRepr.getMatchingPassword())) {
            result.rejectValue("password", "", "Password not matching");
            return "register";
        }

        userRepr.setRoles(new HashSet(Collections.singleton(userService.getAdminRole())));
        userService.create(userRepr);
        return "redirect:/login";
    }
}
