package hu.kesmarki.ocsain.personal_registrar.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Builder
@Data
public class PersonDTO{
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private List<AddressDTO> addresses;
    private List<PersonAvailabilityDTO> personAvailabilities;
}

