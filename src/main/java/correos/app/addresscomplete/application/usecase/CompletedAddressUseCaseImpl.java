package correos.app.addresscomplete.application.usecase;

import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.domain.port.out.AutocompletePort;
import correos.app.addresscomplete.domain.port.out.PlaceDetailsPort;
import correos.app.addresscomplete.domain.port.in.CompletedAddressUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompletedAddressUseCaseImpl implements CompletedAddressUseCase {

    private final AutocompletePort autocomplete;
    private final PlaceDetailsPort placeDetails;

    public CompletedAddressUseCaseImpl(AutocompletePort autocomplete,
                                       PlaceDetailsPort placeDetails) {
        this.autocomplete = autocomplete;
        this.placeDetails = placeDetails;
    }

    public List<String> execute(AddressInput address) {
        List<String> placeIds = autocomplete.fetchPlaceIds(address);
        return placeDetails.getDetailedPlaces(placeIds, address);
    }

}
