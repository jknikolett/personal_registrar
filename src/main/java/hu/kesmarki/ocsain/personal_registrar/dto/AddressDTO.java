package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
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
