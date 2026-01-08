package hu.kesmarki.ocsain.personal_registrar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
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

