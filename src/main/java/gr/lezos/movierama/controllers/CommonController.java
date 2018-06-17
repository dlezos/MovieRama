package gr.lezos.movierama.controllers;

import gr.lezos.movierama.dto.MovieDto;
import gr.lezos.movierama.services.MovieRamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
public class CommonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    MovieRamaService movieRamaService;

    protected final String gotoIndex(String sort, Long userId, Long ownerId, HttpSession session, Model model) {
        List<MovieDto> movieDtos = movieRamaService.getMovies(new Sort(ASC, sort != null ? sort : "title"), userId, ownerId);
        model.addAttribute("ownerId", ownerId);
        model.addAttribute("movies", movieDtos);
        return "index";
    }
}
