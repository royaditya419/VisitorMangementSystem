package io.bootify.visitor_managemen_t_syatem.rest;

import io.bootify.visitor_managemen_t_syatem.model.AddressDTO;
import io.bootify.visitor_managemen_t_syatem.service.AddressService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressResource {

    private final AddressService addressService;

    public AddressResource(final AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(addressService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAddress(@RequestBody @Valid final AddressDTO addressDTO) {
        final Long createdId = addressService.create(addressDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAddress(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AddressDTO addressDTO) {
        addressService.update(id, addressDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAddress(@PathVariable(name = "id") final Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
