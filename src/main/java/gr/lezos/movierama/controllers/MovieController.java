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
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            HttpSession session,
            Model model) {
        if (title == null) {
            // Registration failed, return to the movie page
            model.addAttribute("error", "title-required");
            return "movie";
        }
        if (description == null) {
            // Registration failed, return to the movie page
            model.addAttribute("error", "description-required");
            return "movie";
        }
        if (!movieRamaService.addMovie(title, description, ((UserDto)session.getAttribute("user")).getId())) {
            // Registration failed, return to the movie page
            model.addAttribute("error", "creation-failed");
            return "movie";
        }
        return gotoIndex(null, ((UserDto)session.getAttribute("user")).getId(), ((UserDto)session.getAttribute("user")).getId(), session, model);
    }
}
