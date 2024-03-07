package io.bootify.visitor_managemen_t_syatem.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VisitDTO {

    private Long id;

    @NotNull
    private VisitStatus status;

    private LocalDateTime inTime;

    private LocalDateTime outTime;

    @NotNull
    @Size(max = 255)
    private String purpose;

    @Size(max = 255)
    private String image;

    @NotNull
    @Size(max = 255)
    private String noOFUser;

    private Long vistor;

    private Long flat;

    private Long user;

}
