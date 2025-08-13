package correos.app.addresscomplete.domain.port.out;

import correos.app.addresscomplete.domain.model.AddressInput;

import java.util.List;

public interface AutocompletePort {
    List<String> fetchPlaceIds(AddressInput input);
}
