package correos.app.addresscomplete.infrastructure.google.adapter;

import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.domain.port.out.AutocompletePort;
import correos.app.addresscomplete.infrastructure.google.client.GoogleAutocompleteClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class GoogleAutocompleteAdapter implements AutocompletePort {

    private final GoogleAutocompleteClient client;

    public GoogleAutocompleteAdapter(GoogleAutocompleteClient client) {
        this.client = client;
    }

    @Override
    public List<String> fetchPlaceIds(AddressInput input) {
        return client.fetchPlaceIdsJson(input);
    }

}
