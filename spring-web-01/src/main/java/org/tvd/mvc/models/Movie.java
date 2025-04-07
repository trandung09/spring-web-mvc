package org.tvd.mvc.models;

import com.google.gson.JsonObject;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {

    int id;
    int duration;
    String name;
    String genre;
    String director;
    String description;
    String category;
    LocalDate publishedDate;

    public static JsonObject toJson(Movie movie) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", movie.id);
        jsonObject.addProperty("duration", movie.duration);
        jsonObject.addProperty("name", movie.name);
        jsonObject.addProperty("genre", movie.genre);
        jsonObject.addProperty("director", movie.director);
        jsonObject.addProperty("description", movie.description);
        jsonObject.addProperty("category", movie.category);
        jsonObject.addProperty("publishedDate", movie.publishedDate.toString());

        return jsonObject;
    }
}
