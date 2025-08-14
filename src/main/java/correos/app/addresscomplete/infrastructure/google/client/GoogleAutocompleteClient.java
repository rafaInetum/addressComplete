package correos.app.addresscomplete.infrastructure.google.client;

import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.infrastructure.google.config.GoogleApiProps;
import correos.app.addresscomplete.infrastructure.google.config.GoogleAutocompletePlacesProps;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class GoogleAutocompleteClient {

    private final RestTemplate restTemplate;
    private final GoogleAutocompletePlacesProps autoCompleteApiProps;
    private final GoogleApiProps googleApiProps;

    public GoogleAutocompleteClient(
            RestTemplate restTemplate,
            GoogleAutocompletePlacesProps autoCompleteApiProps,
            GoogleApiProps googleApiProps) {
        this.restTemplate = restTemplate;
        this.autoCompleteApiProps = autoCompleteApiProps;
        this.googleApiProps = googleApiProps;
    }

    public List<String> fetchPlaceIdsJson(AddressInput address) {
        String rawInput = String.join(" ", address.addressLines()) +
                " " + address.locality() +
                " " + address.postalCode();
        return fetchPlaceIds(rawInput);
    }

    public List<String> fetchPlaceIds(String originalAddress) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(autoCompleteApiProps.baseUrl())
                    .queryParam("input", originalAddress)
                    .queryParam("types", "address")
                    .queryParam("language", "es")
                    .queryParam("components", "country:es")
                    .queryParam("key", googleApiProps.apiKey())
                    .toUriString();

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONArray predictions = new JSONObject(response.getBody()).getJSONArray("predictions");

            return IntStream.range(0, predictions.length())
                    .mapToObj(i -> predictions.getJSONObject(i).getString("place_id"))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener placeIds: " + e.getMessage(), e);
        }
    }
}
