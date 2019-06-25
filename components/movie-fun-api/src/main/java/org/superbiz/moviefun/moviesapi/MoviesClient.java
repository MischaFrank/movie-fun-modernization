package org.superbiz.moviefun.moviesapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoviesClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String URL;

    RestOperations restTemplate;

    public MoviesClient(String moviesUrl, RestOperations restOperations) {
        URL = moviesUrl;
        restTemplate = restOperations;
    }

    public MovieInfo find(Long id) {
        return restTemplate.getForObject(URL + "/" + id, MovieInfo.class);
    }

    public void addMovie(MovieInfo movie) {
        logger.debug("Creating movie with title {}, and year {}", movie.getTitle(), movie.getYear());
        logger.debug("Using Path " + URL);
        restTemplate.put(URL, movie);
    }

    public void updateMovie(MovieInfo movie) {
        restTemplate.put(URL + "/" + movie.getId(), movie);
    }

    public void deleteMovie(MovieInfo movie) {
        deleteMovieId(movie.getId());
    }

    public void deleteMovieId(long id) {
        restTemplate.delete(URL + "/" + id);
    }

    public List<MovieInfo> getMovies() {
        return restTemplate.getForObject(URL, List.class);
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        Map<String, Integer> params = new HashMap<>();
        params.put("firstResult", firstResult);
        params.put("maxResults", maxResults);

        return restTemplate.getForObject(URL, List.class, params);
    }

    public int countAll() {
        return restTemplate.getForObject(URL + "/count", int.class);
    }

    public int count(String field, String searchTerm) {

        Map<String, String> params = new HashMap<>();
        params.put("field", field);
        params.put("searchTerm", searchTerm);

        return restTemplate.getForObject(URL + "/search/count", int.class, params);
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        Map<String, String> params = new HashMap<>();
        params.put("field", field);
        params.put("searchTerm", searchTerm);
        params.put("firstResult", String.valueOf(firstResult));
        params.put("maxResults", String.valueOf(maxResults));

        return restTemplate.getForObject(URL + "/search", List.class, params);
    }

    public void clean() {
        restTemplate.delete(URL);
    }
}
