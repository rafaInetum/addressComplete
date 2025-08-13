package correos.app.addresscomplete.infrastructure.google.parser;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class PlaceDetailsParser {

    public String parseFormattedAddress(JsonNode result) {
        String formatted = result.path("formatted_address").asText();
        String postalCode = extractPostalCode(result.path("address_components"));
        return formatted + " " + postalCode;
    }

    private String extractPostalCode(JsonNode components) {
        for (JsonNode comp : components) {
            for (JsonNode type : comp.path("types")) {
                if ("postal_code".equals(type.asText())) {
                    return comp.path("long_name").asText();
                }
            }
        }
        return "";
    }
}
