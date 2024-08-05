package io.javabrains.moviecatalogservice.services;

import io.javabrains.moviecatalogservice.models.UserRating;

public interface UserRatingService {
    UserRating getUserRating(String userId);
}
