package org.tvd.mvc.repositories;

import com.tvd.jsonstorge.utils.FileUtil;
import org.springframework.stereotype.Repository;
import org.tvd.mvc.models.Movie;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepository {

    private final FileUtil<Movie> movieFileUtil = new FileUtil<>();
    private static final String MOVIE_DATA_FILE_NAME = "spring-web-01/src/main/resources/data/movies.json";

    public List<Movie> findAll() throws IOException {
        return movieFileUtil.readDataFromFile(MOVIE_DATA_FILE_NAME, Movie[].class);
    }

    public Optional<Movie> findById(int id) throws IOException {
        List<Movie> movies = findAll();

        return movies.stream().filter(movie -> movie.getId() == id)
                .findFirst();
    }

    public void save(Movie newMovie) throws IOException {
        LinkedList<Movie> movies = new LinkedList<>(movieFileUtil.readDataFromFile(MOVIE_DATA_FILE_NAME, Movie[].class));

        int maxId = movies.stream()
                .map(Movie::getId)
                .max(Integer::compareTo)
                .orElse(0);

        newMovie.setId(maxId + 1);

        movies.addFirst(newMovie);
        movieFileUtil.writeDataToFile(movies, MOVIE_DATA_FILE_NAME);
    }

    public void delete(Movie deleteMovie) throws IOException {
        List<Movie> movies = movieFileUtil.readDataFromFile(MOVIE_DATA_FILE_NAME, Movie[].class);
        movies = movies.stream()
                .filter(movie -> movie != deleteMovie)
                .toList();

        movieFileUtil.writeDataToFile(movies, MOVIE_DATA_FILE_NAME);
    }
}
