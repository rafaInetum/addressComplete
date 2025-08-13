package correos.app.addresscomplete.domain.port.in;

import correos.app.addresscomplete.domain.model.AddressInput;

import java.util.List;

public interface CompletedAddressUseCase {
    List<String> execute(AddressInput address);
}
