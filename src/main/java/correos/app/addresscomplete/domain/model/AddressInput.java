package correos.app.addresscomplete.domain.model;

import java.util.List;

public record AddressInput(
        List<String> addressLines,
        String locality,
        String postalCode,
        String regionCode
) {
}
