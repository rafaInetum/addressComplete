package correos.app.addresscomplete.api.controller;

import correos.app.addresscomplete.application.usecase.CompletedAddressUseCaseImpl;
import correos.app.addresscomplete.domain.model.AddressInput;
import correos.app.addresscomplete.domain.port.in.CompletedAddressUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/address-complete")
public class AddressCompleteController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AddressCompleteController.class);

    private final CompletedAddressUseCase useCase;

    public AddressCompleteController(CompletedAddressUseCaseImpl useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/complete-address")
    public ResponseEntity<List<String>> complete(@RequestBody AddressInput address) {
        return ResponseEntity.ok(useCase.execute(address));
    }
}



