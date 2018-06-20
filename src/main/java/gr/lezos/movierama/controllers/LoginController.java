package gr.lezos.movierama.controllers;

import gr.lezos.movierama.dto.MovieDto;
import gr.lezos.movierama.dto.UserDto;
import gr.lezos.movierama.services.MovieRamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * Web controler for the login
 */
@Controller
public class LoginController extends CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * Handles the login page request
     * @param model
     * @return The login page
     */
    @RequestMapping("/login")
    public String login(HttpSession session, Model model) {
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
            HttpSession session,
            Model model) {
        // Check for required parameters and a not already Logged in user
        if (!username.isEmpty() && !password.isEmpty() && session.getAttribute("user") == null) {
            UserDto userDto = movieRamaService.validateUser(username, password);
            if (userDto != null) {
                session.setAttribute("user", userDto);
                session.removeAttribute("fromRegistration");
                model.addAttribute("user", userDto);
                return gotoIndex("title", userDto.getId(), null, session, model);
            }
        }
        // Login failed, return to the login page
        model.addAttribute("error", model.containsAttribute("user") ? "error-already-logged-in" : "error-login-failed");
        return "login";
    }

    /**
     * Handles the logout action
     * @return The index page
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "index";
    }

}
