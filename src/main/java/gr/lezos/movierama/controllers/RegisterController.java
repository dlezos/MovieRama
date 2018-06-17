package gr.lezos.movierama.controllers;

import gr.lezos.movierama.services.MovieRamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Web controller for register user page
 */
@Controller
public class RegisterController extends CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    /**
     * Handles the register page request
     * @param model
     * @return The login page
     */
    @RequestMapping("/register")
    public String register(Model model) {
        return "register";
    }

    /**
     * Handles the login action
     * @param username The username
     * @param password The password
     * @param model The model
     * @return The register page in failure, The index page in success
     */
    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        Boolean success = true;
        return success ? "index" : "register";
    }

}
