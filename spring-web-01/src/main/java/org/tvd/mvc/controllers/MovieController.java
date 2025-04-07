package org.tvd.mvc.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.tvd.mvc.services.movie.IMovieService;

import org.tvd.mvc.models.Movie;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/movies")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieController {

    IMovieService movieService;

    @GetMapping
    public String index(Model model) {

        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "create-movie";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/movies";
    }

    @PostMapping("/delete/{movieId}")
    public String delete(@PathVariable int movieId) {
        Movie deleteMovie = movieService.getMovieDetails(movieId)
                .orElseThrow(() -> new NoSuchElementException("Cannot find movie with id: " + movieId));
        movieService.deleteMovie(deleteMovie);
        return "redirect:/movies";
    }
}

