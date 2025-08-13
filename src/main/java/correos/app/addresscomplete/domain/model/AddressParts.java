package correos.app.addresscomplete.domain.model;

public record AddressParts(
        String street,
        String number,
        String postalCode,
        String city
) {}
