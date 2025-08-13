package correos.app.addresscomplete.infrastructure.google.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.domain.model.AddressParts;
import correos.app.addresscomplete.domain.model.ScoredAddress;
import correos.app.addresscomplete.domain.service.SuggestionRanker;
import correos.app.addresscomplete.infrastructure.google.config.GooglePlaceDetailsProps;
import correos.app.addresscomplete.infrastructure.google.parser.AddressPartsParser;
import correos.app.addresscomplete.infrastructure.google.parser.PlaceDetailsParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class GooglePlaceDetailsClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final GooglePlaceDetailsProps props;
    private final PlaceDetailsParser parser;
    private final SuggestionRanker ranker;
    private final AddressPartsParser addressPartsParser;

    public GooglePlaceDetailsClient(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            GooglePlaceDetailsProps props,
            PlaceDetailsParser parser,
            SuggestionRanker ranker,
            AddressPartsParser addressPartsParser) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.props = props;
        this.parser = parser;
        this.ranker = ranker;
        this.addressPartsParser = addressPartsParser;
    }


    public List<String> execute(List<String> placeIds, AddressInput address) {

        List<ScoredAddress> scored = placeIds.stream()
                .map(this::detailedPlaces)
                .filter(Objects::nonNull)
                .toList();

        return ranker.sort(address, scored);
    }

    private ScoredAddress detailedPlaces(String placeId) {
        try {
            String url = buildUrl(placeId);

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode result = mapper.readTree(response.getBody()).path("result");

            String formatted = parser.parseFormattedAddress(result);
            AddressParts parts = addressPartsParser.parse(result);

            return new ScoredAddress(formatted, parts);
        } catch (RestClientResponseException e) {
            log.error("Error HTTP al consultar Place Details para {}. status={} body={}", placeId,
                    e.getRawStatusCode(), e.getResponseBodyAsString(), e);
            return null;
        } catch (Exception e) {
            log.error("Error al procesar Place Details para {}", placeId, e);
            return null;
        }
    }

    private String buildUrl(String placeId) {
        return UriComponentsBuilder
                .fromHttpUrl(props.baseUrl())
                .queryParam("place_id", placeId)
                .queryParam("fields", "formatted_address,address_components")
                .queryParam("language", "es")
                .queryParam("key", props.apiKey())
                .toUriString();
    }
}
