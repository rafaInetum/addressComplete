package correos.app.addresscomplete.infrastructure.google.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.places.details")
public record GooglePlaceDetailsProps(String baseUrl) {
}
