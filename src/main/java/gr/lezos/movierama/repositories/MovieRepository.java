package gr.lezos.movierama.repositories;

import gr.lezos.movierama.model.Movie;
import gr.lezos.movierama.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository  extends JpaRepository<Movie, Long> {
    List<Movie> findAll();
    List<Movie> findAll(Sort sort);
    List<Movie> findAllByOwner(User owner);
    Movie findByTitle(String title);

    /**
     * This is a query with possibly increased complexity, the "likes" and "hates" values could either:
     * a. Be moved to the MovieDto entity and be updates as Vote are INSERTED or UPDATED
     * b. Be moved as a subselect
     * c. Be counted and sorted in memory by Java code
     */
    @Query( " SELECT m, count(l) as likes, count(h) as hates" +
            "  FROM Movie m" +
            "       LEFT OUTER JOIN Vote l on l.movie = m AND l.opinion = TRUE" +
            "       LEFT OUTER JOIN Vote h on h.movie = m AND h.opinion = FALSE" +
            " GROUP BY m")
    List<Object[]> findAllWithLikes(Sort sort);
}
