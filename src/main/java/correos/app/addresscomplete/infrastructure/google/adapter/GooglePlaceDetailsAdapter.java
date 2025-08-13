package correos.app.addresscomplete.infrastructure.google.adapter;

import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.domain.port.out.PlaceDetailsPort;
import correos.app.addresscomplete.infrastructure.google.client.GooglePlaceDetailsClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GooglePlaceDetailsAdapter implements PlaceDetailsPort {

    private final GooglePlaceDetailsClient client;

    public GooglePlaceDetailsAdapter(GooglePlaceDetailsClient client) {
        this.client = client;
    }

    @Override
    public List<String> getDetailedPlaces(List<String> placeIds, AddressInput input) {
        return client.execute(placeIds, input);
    }
}
