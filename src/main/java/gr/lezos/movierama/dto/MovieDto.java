package gr.lezos.movierama.dto;

/**
 * The movie data for the web components
 */
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private UserDto owner;
    private Long created;
    private Long likes;
    private Long hates;
    private Boolean opinion;

    public MovieDto(Long id, String title, String description, UserDto owner, Long created, Long likes, Long hates, Boolean opinion) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.created = created;
        this.likes = likes;
        this.hates = hates;
        this.opinion = opinion;
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
}
