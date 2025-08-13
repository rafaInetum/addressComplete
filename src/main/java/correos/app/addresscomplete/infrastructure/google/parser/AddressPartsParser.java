package correos.app.addresscomplete.infrastructure.google.parser;

import com.fasterxml.jackson.databind.JsonNode;
import correos.app.addresscomplete.domain.model.AddressParts;
import org.springframework.stereotype.Component;

@Component
public class AddressPartsParser {

    public AddressParts parse(JsonNode result) {
        String street = null;
        String number = null;
        String postalCode = null;
        String city = null;

        JsonNode components = result.path("address_components");

        if (components.isArray()) {
            for (JsonNode component : components) {
                String longName = component.path("long_name").asText();
                for (JsonNode typeNode : component.path("types")) {
                    String type = typeNode.asText();

                    switch (type) {
                        case "route" -> street = longName;
                        case "street_number" -> number = longName;
                        case "postal_code" -> postalCode = longName;
                        case "locality", "postal_town" -> city = longName;
                    }
                }
            }
        }

        return new AddressParts(street, number, postalCode, city);
    }
}
