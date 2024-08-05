package io.javabrains.moviecatalogservice.services;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CatalogService {
    List<CatalogItem> getCatalog(String userId);
}
