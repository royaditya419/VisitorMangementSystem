package io.bootify.visitor_managemen_t_syatem.model;

import io.bootify.visitor_managemen_t_syatem.domain.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private Long phone;

    @Size(max = 255)
    private String role;

    private UserStatus status;

    private String flat_Number;

    private AddressDTO address;

}
