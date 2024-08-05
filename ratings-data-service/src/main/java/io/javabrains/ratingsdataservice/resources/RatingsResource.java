package io.javabrains.ratingsdataservice.resources;

import io.javabrains.ratingsdataservice.model.Rating;
import io.javabrains.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/movies/{movieId}")
    public Rating getMovieRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {

        UserRating userRating = new UserRating();
        userRating.initData(userId);
        return userRating;

        /*
        List<Rating> ratings = Arrays.asList(
               new Rating("100", 3),
               new Rating("200", 4)
       );

       UserRating userRating = new UserRating();
       userRating.setRatings(ratings);
       return userRating;
       */
    }

}
