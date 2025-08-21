package correos.app.addresscomplete.api.controller;

import correos.app.addresscomplete.application.port.in.CompletedAddressUseCase;
import correos.app.addresscomplete.domain.model.AddressInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/address-complete")
public class AddressCompleteController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AddressCompleteController.class);

    private final CompletedAddressUseCase useCase;

    public AddressCompleteController(CompletedAddressUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/complete-address")
    public ResponseEntity<List<String>> complete(@RequestBody AddressInput address) {
        return ResponseEntity.ok(useCase.execute(address));
    }
}



