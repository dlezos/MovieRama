package gr.lezos.movierama.model;

import org.hibernate.mapping.ToOne;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Vote database entity
 */
@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * The opinion of the vote: TRUE=Like, FALSE=Hate
     */
    @Column
    private Boolean opinion;

    /**
     * The movie this vote is set on
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    private Movie movie;

    /**
     * The user that submitted the vote
     */
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOpinion() {
        return opinion;
    }

    public void setOpinion(Boolean opinion) {
        this.opinion = opinion;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
