package io.bootify.visitor_managemen_t_syatem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VisitorDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String name;

    private Long phone;

    @NotNull
    @Size(max = 255)

    private String iDNumber;

    private AddressDTO address;

}
