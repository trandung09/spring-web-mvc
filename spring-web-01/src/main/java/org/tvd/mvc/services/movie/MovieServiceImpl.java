package org.tvd.mvc.services.movie;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.tvd.mvc.models.Movie;
import org.tvd.mvc.repositories.MovieRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieServiceImpl implements IMovieService {

    MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        try {
            return movieRepository.findAll();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load movies from database: " + e.getMessage());
        }
    }

    @Override
    public Optional<Movie> getMovieDetails(int id) {
        if (id < 0) {
            throw new RuntimeException("Invalid movie id provided: " + id);
        }
        try {
            return Optional.ofNullable(movieRepository.findById(id)
                    .orElseThrow(NoSuchElementException::new));
        } catch (Exception e) {
            throw new RuntimeException("Can not get movie by id: " + id);
        }
    }

    @Override
    public void saveMovie(Movie newMovie) {
        if (newMovie == null) {
            throw new RuntimeException("Invalid movie provided: " + newMovie);
        }
        try {
            movieRepository.save(newMovie);
        } catch (Exception e) {
            throw new RuntimeException("Can not save movie: " + newMovie);
        }
    }

    @Override
    public void deleteMovie(Movie deleteMovie) {
        if (deleteMovie == null) {
            throw new RuntimeException("Invalid movie provided: " + deleteMovie);
        }
        try {
            movieRepository.delete(deleteMovie);
        } catch (Exception e) {
            throw new RuntimeException("Can not delete movie: " + deleteMovie);
        }
    }
}
