package io.javabrains.moviecatalogservice.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "catalog")
public class CatalogProperties {

    private final CatalogProperties.Backends backends = new CatalogProperties.Backends();

    public CatalogProperties.Backends getBackends() {
        return this.backends;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Backends {

        private String movieInfoServiceUrl;
        private String ratingsDataServiceUrl;
    }
}
