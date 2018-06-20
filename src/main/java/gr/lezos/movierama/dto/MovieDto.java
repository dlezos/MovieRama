package gr.lezos.movierama.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The movie data for the web components
 */
public class MovieDto {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieDto.class);

    private Long id;
    private String title;
    private String description;
    private UserDto owner;
    private Long created;
    private Long likes;
    private Long hates;
    private Boolean opinion;
    private Boolean canLike;
    private Boolean canHate;

    public MovieDto(Long id, String title, String description, UserDto owner, Long created, Long likes, Long hates, Boolean opinion, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.created = created;
        this.likes = likes;
        this.hates = hates;
        this.opinion = opinion;
        this.canLike = (opinion == null || opinion == Boolean.FALSE) && owner != null && !owner.getId().equals(userId) && userId != null;
        this.canHate = (opinion == null || opinion == Boolean.TRUE) && owner != null && !owner.getId().equals(userId) && userId != null;
        LOGGER.info("opinion {}, owner {}, userId {}, canLike {}, canHate {}", opinion, owner, userId, this.canLike, this.canHate);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UserDto getOwner() {
        return owner;
    }

    public Long getCreated() {
        return created;
    }

    public Long getLikes() {
        return likes;
    }

    public Long getHates() {
        return hates;
    }

    public Boolean getOpinion() {
        return opinion;
    }

    public Boolean getCanLike() {
        return canLike;
    }

    public void setCanLike(Boolean canLike) {
        this.canLike = canLike;
    }

    public Boolean getCanHate() {
        return canHate;
    }

    public void setCanHate(Boolean canHate) {
        this.canHate = canHate;
    }
}
