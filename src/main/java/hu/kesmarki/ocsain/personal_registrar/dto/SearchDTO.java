package hu.kesmarki.ocsain.personal_registrar.dto;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressAvailabilityType;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressType;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.PersonAvailabilityType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class SearchDTO {

    private String firstName;
    private String lastName;
    private LocalDate fromDateOfBirth;
    private LocalDate toDateOfBirth;
    private PersonAvailabilityType personAvailabilityType;
    private String personAvailability;
    private AddressType addressType;
    private String zipCode;
    private String city;
    private String addressLine;
    private AddressAvailabilityType addressAvailabilityType;
    private String addressAvailability;

}
