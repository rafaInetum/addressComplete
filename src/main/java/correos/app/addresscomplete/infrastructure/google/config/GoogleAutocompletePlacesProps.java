package correos.app.addresscomplete.infrastructure.google.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.places.autocomplete")
public record GoogleAutocompletePlacesProps(String baseUrl, String apiKey) {}
