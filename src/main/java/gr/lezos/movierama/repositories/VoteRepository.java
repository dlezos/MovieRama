package gr.lezos.movierama.repositories;

import gr.lezos.movierama.model.User;
import gr.lezos.movierama.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByUser(User user);
}
