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
 * Web controller for movie page
 */
@Controller
public class MovieController extends CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    /**
     * Handles the register movie page request
     * @param model
     * @return The login page
     */
    @RequestMapping("/movie")
    public String movie(Model model) {
        return "movie";
    }

    /**
     * Handles the submit movie action
     * @param username The username
     * @param password The password
     * @param model The model
     * @return The login page in failure, The index page in success
     */
    @PostMapping("/movie")
    public String movie(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {
        Boolean success = true;
        return success ? "index" : "login";
    }
}
