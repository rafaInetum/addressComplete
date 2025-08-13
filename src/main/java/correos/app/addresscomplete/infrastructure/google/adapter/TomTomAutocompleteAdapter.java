package correos.app.addresscomplete.infrastructure.google.adapter;

import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.domain.port.out.AutocompletePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TomTomAutocompleteAdapter implements AutocompletePort {
    @Override
    public List<String> fetchPlaceIds(AddressInput input) {
        System.out.println("prueba implementacion TomTomAutocompleteAdapter.fetchPlaceIds");
        return List.of("-----");
    }
}
