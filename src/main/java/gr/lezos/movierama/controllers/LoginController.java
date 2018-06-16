package gr.lezos.movierama.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /**
     * Handles the login page request
     * @param model
     * @return The login page
     */
    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }

    /**
     * Handles the login action
     * @param username The username
     * @param password The password
     * @param model The model
     * @return The login page in failure, The index page in success
     */
    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        Boolean success = true;
        return success ? "index" : "login";
    }

}
