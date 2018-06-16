package gr.lezos.movierama.repositories;

import gr.lezos.movierama.model.Movie;
import gr.lezos.movierama.model.User;
import gr.lezos.movierama.model.Vote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * Test the Repositories
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VoteRepository voteRepository;

    @Before
    public void setUp() throws Exception {
        // Register a User Creator
        User creator = new User();
        creator.setUsername("CreatorUser");
        userRepository.save(creator);
        // Creator submits MovieA
        Movie movieA = new Movie();
        movieA.setTitle("MovieA");
        movieA.setOwner(creator);
        movieRepository.save(movieA);
        // Creator submits MovieB
        Movie movieB = new Movie();
        movieB.setOwner(creator);
        movieB.setTitle("MovieB");
        movieRepository.save(movieB);
        // Creator submits MovieC
        Movie movieC = new Movie();
        movieC.setOwner(creator);
        movieC.setTitle("MovieC");
        movieRepository.save(movieC);
        // Register a User VoterOne
        User voterOne = new User();
        voterOne.setUsername("voterOne");
        userRepository.save(voterOne);
        // VoterOne likes MovieA
        Vote voteOneA = new Vote();
        voteOneA.setOpinion(Boolean.TRUE);
        voteOneA.setMovie(movieA);
        voteOneA.setUser(voterOne);
        voteRepository.save(voteOneA);
        // VoterOne hates MovieB
        Vote voteOneB = new Vote();
        voteOneB.setOpinion(Boolean.FALSE);
        voteOneB.setMovie(movieB);
        voteOneB.setUser(voterOne);
        voteRepository.save(voteOneB);
        // Register a User VoterTwo
        User voterTwo = new User();
        voterTwo.setUsername("voterTwo");
        userRepository.save(voterTwo);
        // VoterTwo likes MovieA
        Vote voteTwoA = new Vote();
        voteTwoA.setOpinion(Boolean.TRUE);
        voteTwoA.setMovie(movieA);
        voteTwoA.setUser(voterTwo);
        voteRepository.save(voteTwoA);
        // VoterTwo likes MovieB
        Vote voteTwoB = new Vote();
        voteTwoB.setOpinion(Boolean.TRUE);
        voteTwoB.setMovie(movieB);
        voteTwoB.setUser(voterTwo);
        voteRepository.save(voteTwoB);
        // So the Votes are as follows
        // MovieA likes 2 hates 0
        // MovieB likes 1 hates 1
        // MovieC likes 0 hates 0
    }

    @Test
    public void findAllAndSortByLike() {
        List<Object[]> result = movieRepository.findAllAndSortByLikes(new Sort(ASC, "likes", "title"));
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("MovieC", ((Movie)result.get(0)[0]).getTitle());
        assertEquals(0L, result.get(0)[1]);
        assertEquals(0L, result.get(0)[2]);
        assertEquals("MovieB", ((Movie)result.get(1)[0]).getTitle());
        assertEquals(1L, result.get(1)[1]);
        assertEquals(1L, result.get(1)[2]);
        assertEquals("MovieA", ((Movie)result.get(2)[0]).getTitle());
        assertEquals(2L, result.get(2)[1]);
        assertEquals(0L, result.get(2)[2]);
    }

    @Test
    public void findUser() {
        User user = userRepository.findByUsername("voterOne");
        assertNotNull(user);
        List<Vote> votes = voteRepository.findByUser(user);
        assertNotNull(votes);
        assertEquals(2, votes.size());
    }
}