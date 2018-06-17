package gr.lezos.movierama.controllers;

import gr.lezos.movierama.dto.UserDto;
import gr.lezos.movierama.services.MovieRamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            HttpSession session,
            Model model) {
        if (username == null) {
            // Registration failed, return to the register page
            model.addAttribute("error", "username-required");
            return "register";
        }
        if (password == null) {
            // Registration failed, return to the register page
            model.addAttribute("error", "password-required");
            return "register";
        }
        UserDto userDto = movieRamaService.registerUser(username, password, name, surname, email);
        if (userDto == null) {
            // Registration failed, return to the register page
            model.addAttribute("error", "username-exists");
            return "register";
        }
        session.setAttribute("user", userDto);
        session.setAttribute("fromRegistration", true);
        model.addAttribute("user", userDto);
        return gotoIndex("title", userDto.getId(), null, session, model);
    }

}
