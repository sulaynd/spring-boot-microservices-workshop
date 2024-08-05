package io.javabrains.moviecatalogservice.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.properties.CatalogProperties;
import io.javabrains.moviecatalogservice.services.MovieInfoService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {

    private final RestTemplate restTemplate;
    private final String movieInfoServiceUrl;

    public MovieInfoServiceImpl(CatalogProperties applicationProperties, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.movieInfoServiceUrl = applicationProperties.getBackends().getMovieInfoServiceUrl();
    }

    //https://erolyapici.medium.com/hystrix-configuration-in-properties-file-2fc90e330f08
    @Override
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
    threadPoolKey = "movieInfoThreadPool",
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "50")

    },
    threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
    }
    )
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject(movieInfoServiceUrl + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }


    private CatalogItem getFallbackCatalogItem(Rating rating){
        return new CatalogItem("No Movie", "", rating.getRating());
    }
}
