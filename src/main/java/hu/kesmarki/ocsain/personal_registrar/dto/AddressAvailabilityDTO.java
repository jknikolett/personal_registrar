package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressAvailabilityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddressAvailabilityDTO {

    private Long id;
    private AddressAvailabilityType addressAvailabilityType;
    private String availability;
}
