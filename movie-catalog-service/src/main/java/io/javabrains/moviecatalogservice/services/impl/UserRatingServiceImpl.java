package io.javabrains.moviecatalogservice.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.properties.CatalogProperties;
import io.javabrains.moviecatalogservice.services.UserRatingService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingServiceImpl implements UserRatingService {

    private final RestTemplate restTemplate;
    private final String ratingsDataServiceUrl;

    public UserRatingServiceImpl(CatalogProperties applicationProperties, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.ratingsDataServiceUrl = applicationProperties.getBackends().getRatingsDataServiceUrl();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject(ratingsDataServiceUrl + userId, UserRating.class);
    }
    private UserRating getFallbackUserRating(String userId){
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(
                new Rating("0", 0)
        ));
        return userRating;
    }
}
