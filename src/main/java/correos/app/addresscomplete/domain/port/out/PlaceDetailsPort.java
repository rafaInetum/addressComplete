package correos.app.addresscomplete.domain.port.out;

import correos.app.addresscomplete.domain.model.AddressInput;

import java.util.List;

public interface PlaceDetailsPort {
    List<String> getDetailedPlaces(List<String> placeIds, AddressInput input);
}
