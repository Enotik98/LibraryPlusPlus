package com.libraryplusplus.service;

import com.libraryplusplus.dto.GenreDTO;
import com.libraryplusplus.entity.Genre;
import com.libraryplusplus.repository.GenreRepository;
import com.libraryplusplus.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public List<Genre> findAllGenre() {
        try {
            return genreRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    public void addGenre(GenreDTO genreDTO) {
        try {
            Genre exGenre = genreRepository.findByName(genreDTO.getName());
            if (exGenre != null){
                throw new CustomException(HttpStatus.CONFLICT, "Genre with this name already exists");
            }
            Genre genre = genreDTO.ConvertToGenre();
            genreRepository.save(genre);
        } catch (CustomException customException) {
            throw new CustomException(customException.getStatus(), customException.getMessage());
        } catch (Exception e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
