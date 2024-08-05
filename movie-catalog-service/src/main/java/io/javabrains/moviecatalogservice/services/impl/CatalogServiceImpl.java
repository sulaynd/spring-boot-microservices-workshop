package io.javabrains.moviecatalogservice.services.impl;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.CatalogService;
import io.javabrains.moviecatalogservice.services.MovieInfoService;
import io.javabrains.moviecatalogservice.services.UserRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService {

    private final WebClient.Builder webClientBuilder;
    private final MovieInfoService movieInfoService;
    private final UserRatingService userRatingService;



    public CatalogServiceImpl(WebClient.Builder webClientBuilder, MovieInfoService movieInfoService, UserRatingService userRatingService) {
        this.webClientBuilder = webClientBuilder;
        this.movieInfoService = movieInfoService;
        this.userRatingService = userRatingService;
    }

    @Override
    public List<CatalogItem> getCatalog(String userId) {

        UserRating userRating = userRatingService.getUserRating(userId);

        return userRating.getRatings().stream()
                .map(movieInfoService::getCatalogItem)
                //.map(rating ->movieInfoService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }

}


        /*
         // UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
         // Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        // Movie movie = restTemplate.getForObject(movieInfoServiceUrl + rating.getMovieId(), Movie.class);
        /* Movie movie = webClientBuilder.build().get().uri(movieInfoServiceUrl + rating.getMovieId())
                        .retrieve().bodyToMono(Movie.class).block();*/
        //return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
         /* return userRating.getRatings().stream()
                .map(rating -> {
                    // Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                   // Movie movie = restTemplate.getForObject(movieInfoServiceUrl + rating.getMovieId(), Movie.class);
                  //Movie movie = webClientBuilder.build().get().uri(movieInfoServiceUrl + rating.getMovieId())
                   //     .retrieve().bodyToMono(Movie.class).block();
        //return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
       // return getCatalogItem(rating);
            //})
        //    .collect(Collectors.toList());
       */





    /*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
Mono() is a reactive way of getting and object back but not right way (asynchronous object)
Block(): blocking execution till this Mono() is fulfill
*/


