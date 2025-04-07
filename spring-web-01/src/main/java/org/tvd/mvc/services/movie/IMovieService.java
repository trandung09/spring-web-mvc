package org.tvd.mvc.services.movie;

import org.tvd.mvc.models.Movie;

import java.util.List;
import java.util.Optional;

public interface IMovieService {

    List<Movie> getAllMovies();
    Optional<Movie> getMovieDetails(int id);
    void saveMovie(Movie movie);
    void deleteMovie(Movie movie);
}
