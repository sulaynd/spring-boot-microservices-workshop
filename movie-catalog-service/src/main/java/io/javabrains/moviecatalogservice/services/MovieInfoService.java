package io.javabrains.moviecatalogservice.services;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Rating;

public interface MovieInfoService {

    CatalogItem getCatalogItem(Rating rating);
}
