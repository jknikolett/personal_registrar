package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.PersonAvailabilityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonAvailabilityDTO {
    private Long id;

    @NotNull
    private PersonAvailabilityType personAvailabilityType;

    @NotNull
    @Size(max=200)
    private String availability;
}
