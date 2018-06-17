package gr.lezos.movierama.services;

import gr.lezos.movierama.dto.MovieDto;
import gr.lezos.movierama.dto.UserDto;
import gr.lezos.movierama.model.Movie;
import gr.lezos.movierama.model.User;
import gr.lezos.movierama.model.Vote;
import gr.lezos.movierama.repositories.MovieRepository;
import gr.lezos.movierama.repositories.UserRepository;
import gr.lezos.movierama.repositories.VoteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieRamaService {
    MovieRepository movieRepository;
    UserRepository userRepository;
    VoteRepository voteRepository;

    /**
     * Constructor
     * @param movieRepository The movie repository autowired
     * @param userRepository The user repository autowired
     * @param voteRepository The vote repository autowired
     */
    public MovieRamaService(MovieRepository movieRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    };

    public UserDto validateUser(String username, String password) {
        Assert.notNull(username, "error.username.null");
        Assert.notNull(password, "error.password.null");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return fromUser(user);
    }

    public List<MovieDto> getMovies(Sort sort, Long userId, Long ownerId) {
        // Get the owner user
        User owner = null;
        if (ownerId != null) {
            owner = userRepository.getOne(ownerId);
        }
        // In case we want all the movies
        if (owner == null) {
            List<Object[]> movies = movieRepository.findAllWithLikes(sort);
            return movies.stream().map(a -> fromArray(a, userId)).collect(Collectors.toList());
        }
        // Get the movies of a specific owner
        List<Movie> movies = movieRepository.findAllByOwner(owner);
        return movies.stream().map(m -> fromMovie(m, userId)).collect(Collectors.toList());
    }

    public void vote(Long movieId, Long userId, Boolean opinion) {
        // Get the movie
        Movie movie = movieRepository.getOne(movieId);
        // Get the user
        User user = userRepository.getOne(userId);
        // Verify the provided parameters
        if (movie == null || user == null) {
            return;
        }
        // Remove any existing vote for this movie by this user
        Vote vote = voteRepository.findByMovieAndUser(movie, user);
        if (vote != null && opinion == null) {
            user.getVotes().remove(vote);
            userRepository.save(user);
            movie.getVotes().remove(vote);
        }
        if (opinion != null) {
            // Add a vote with the new opinion
            if (vote == null) {
                vote = new Vote();
                vote.setMovie(movie);
                vote.setUser(user);
                user.getVotes().add(vote);
            }
            vote.setOpinion(opinion);
            voteRepository.save(vote);
        }
    }

    private static final MovieDto fromArray(Object[] array, Long userId){
        Movie movie = (Movie)array[0];
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                fromUser(movie.getOwner()),
                movie.getPublicationDate() != null ? Math.abs(Duration.between(movie.getPublicationDate(), LocalDateTime.now()).toDays()) : 0,
                (Long)array[1],
                (Long)array[2],
                movie.getVotes().stream().filter(v -> v.getUser().getId().equals(userId)).findAny().orElse(new Vote()).getOpinion(),
                userId
        );
    }

    private static final MovieDto fromMovie(Movie movie, Long userId) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                fromUser(movie.getOwner()),
                movie.getPublicationDate() != null ? Math.abs(Duration.between(movie.getPublicationDate(), LocalDateTime.now()).toDays()) : 0,
                movie.getVotes() != null ? movie.getVotes().stream().filter(v -> v.getOpinion() == Boolean.TRUE).count() : 0,
                movie.getVotes() != null ? movie.getVotes().stream().filter(v -> v.getOpinion() == Boolean.FALSE).count() : 0,
                movie.getVotes() != null ? movie.getVotes().stream().filter(v -> v.getUser().getId().equals(userId)).findAny().orElse(new Vote()).getOpinion() : null,
                userId
        );
    }

    private static final UserDto fromUser(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getSurname());
    }

    public UserDto registerUser(String username, String password, String name, String surname, String email) {
        // Get a user with the provided username
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return null;
        }
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setName(name);
        user.setSurname(surname);
        user.setPassword(password);
        userRepository.save(user);
        return fromUser(user);
    }

    public boolean addMovie(String title, String description, Long userId) {
        // Get the user
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return false;
        }
        // Add the Movie
        Movie movie = new Movie();
        movie.setOwner(user);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setPublicationDate(LocalDateTime.now());
        movieRepository.save(movie);
        return true;
    }
}
