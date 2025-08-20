package correos.app.addresscomplete.application.service;

import correos.app.addresscomplete.application.port.in.CompletedAddressUseCase;
import correos.app.addresscomplete.application.port.out.PlaceDetailsPort;
import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.application.port.out.AutocompletePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompletedAddressService implements CompletedAddressUseCase {

    private final AutocompletePort autocomplete;
    private final PlaceDetailsPort placeDetails;

    public CompletedAddressService(AutocompletePort autocomplete,
                                   PlaceDetailsPort placeDetails) {
        this.autocomplete = autocomplete;
        this.placeDetails = placeDetails;
    }

    public List<String> execute(AddressInput address) {
        List<String> placeIds = autocomplete.fetchPlaceIds(address);
        return placeDetails.getDetailedPlaces(placeIds, address);
    }

}
