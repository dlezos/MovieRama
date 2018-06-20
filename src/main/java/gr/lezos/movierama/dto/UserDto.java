package gr.lezos.movierama.dto;

import gr.lezos.movierama.model.Movie;

import java.util.List;

/**
 * UserDto data for the web components
 */
public class UserDto {
    private Long id;
    private String username;
    private String name;
    private String surname;

    public UserDto(Long id, String username, String name, String surname) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.surname = surname;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
