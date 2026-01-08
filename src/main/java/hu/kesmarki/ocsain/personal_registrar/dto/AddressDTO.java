package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddressDTO {
    private Long id;
    private AddressType addressType;
    private String zipCode;
    private String city;
    private String addressLine;
    private List<AddressAvailabilityDTO> addressAvailabilities;
}
