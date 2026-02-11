package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddressDTO {
    private Long id;
    @NotNull
    private AddressType addressType;
    @NotNull
    @Size(max=10)
    private String zipCode;
    @NotNull
    @Size(max=200)
    private String city;
    @NotNull
    @Size(max=1000)
    private String addressLine;
    @Builder.Default
    private List<@Valid AddressAvailabilityDTO> addressAvailabilities = new ArrayList<>();
}
