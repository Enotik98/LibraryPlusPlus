package com.libraryplusplus.dto;

import com.libraryplusplus.entity.Book;
import com.libraryplusplus.entity.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class GenreDTO {
    private int id;
    @NotEmpty(message = "Genre mustn't be empty")
    private String name;
    public Genre ConvertToGenre() {
        Genre genre = new Genre();
        genre.setId(this.getId());
        genre.setName(this.getName());
        return genre;
    }

}
