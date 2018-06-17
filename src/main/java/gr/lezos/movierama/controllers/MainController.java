package gr.lezos.movierama.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Web controller for the index page
 */
@Controller
public class MainController extends CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/")
    public String index(
            @RequestParam("sort") Optional<String> sort,
            @RequestParam("userId") Optional<Long> userId,
            @RequestParam("ownerId") Optional<Long> ownerId,
            HttpSession session,
            Model model
    ) {
        return gotoIndex(sort.orElse(null), userId.orElse(null), ownerId.orElse(null), session, model);
    }

    @RequestMapping("/like")
    public String like(@RequestParam("movieId") Long movieId, @RequestParam("userId") Long userId, HttpSession session, Model model) {
        movieRamaService.vote(movieId, userId, Boolean.TRUE);
        return gotoIndex(null, userId, null, session, model);
    }

    @RequestMapping("/hate")
    public String hate(@RequestParam Long movieId, @RequestParam("userId") Long userId, HttpSession session, Model model) {
        movieRamaService.vote(movieId, userId, Boolean.FALSE);
        return gotoIndex(null, userId, null, session, model);
    }

    @RequestMapping("/unset")
    public String unset(@RequestParam Long movieId, @RequestParam("userId") Long userId, HttpSession session, Model model) {
        movieRamaService.vote(movieId, userId, null);
        return gotoIndex(null, userId, null, session, model);
    }
}
