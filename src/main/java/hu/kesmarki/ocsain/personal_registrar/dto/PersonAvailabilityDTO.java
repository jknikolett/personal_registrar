package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.PersonAvailabilityType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonAvailabilityDTO {
    private Long id;
    private PersonAvailabilityType personAvailabilityType;
    private String availability;
}
