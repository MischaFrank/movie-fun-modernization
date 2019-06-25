package org.superbiz.moviefun.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/movies")
public class MoviesController {

    MoviesRepository moviesRepository;


    public MoviesController(@Autowired MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @GetMapping("/{id}")
    public Movie find(@PathVariable Long id) {
        return moviesRepository.find(id);
    }

    @PutMapping
    public void addMovie(@RequestBody Movie movie) {
        moviesRepository.addMovie(movie);
    }

    @PutMapping(path = "/{id}")
    public void updateMovie(@RequestBody Movie movie, @PathVariable Long id) {
        movie.setId(id);
        moviesRepository.updateMovie(movie);
    }


    @DeleteMapping(path = "/{id}")
    public void deleteMovieId(@PathVariable long id) {
        moviesRepository.deleteMovieId(id);
    }

    public List<Movie> getMovies() {
        return moviesRepository.getMovies();
    }

    @GetMapping
    public List<Movie> findAll(@RequestParam(name = "firstResult", required = false) Integer firstResult, @RequestParam(name = "maxResults", required = false) Integer maxResults) {
        if (firstResult != null && maxResults != null) {
            return moviesRepository.findAll(firstResult, maxResults);
        } else {
            return getMovies();
        }

    }

    @GetMapping(path = "/count")
    public int countAll() {
        return moviesRepository.countAll();
    }

    @GetMapping(path = "/search/count")
    public int count(@RequestParam(name = "field", required = false) String field, @RequestParam(name = "searchTerm", required = false) String searchTerm) {
        if (field != null && searchTerm != null) {
            return moviesRepository.count(field, searchTerm);
        } else {
            return moviesRepository.countAll();
        }
    }

    @GetMapping(path = "/search")
    public List<Movie> findRange(
            @RequestParam(name = "field", required = false) String field,
            @RequestParam(name = "searchTerm", required = false) String searchTerm,
            @RequestParam(name = "firstResult", required = false) Integer firstResult,
            @RequestParam(name = "maxResults", required = false) Integer maxResults) {
        if (field != null && searchTerm != null) {
            return moviesRepository.findRange(field, searchTerm, firstResult, maxResults);
        } else if (firstResult != null && maxResults != null) {
            return moviesRepository.findAll(firstResult, maxResults);
        } else {
            return moviesRepository.getMovies();
        }
    }

    @DeleteMapping
    public void clean() {
        moviesRepository.clean();
    }
}
