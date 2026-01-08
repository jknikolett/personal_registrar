package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressAvailabilityType;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class AddressAvailabilityDTO {

    private Long id;
    private AddressAvailabilityType addressAvailabilityType;
    private String availability;
}
