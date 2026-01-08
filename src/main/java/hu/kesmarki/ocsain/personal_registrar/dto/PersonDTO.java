package hu.kesmarki.ocsain.personal_registrar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @Size(max=200)
    private String firstName;

    @NotNull
    @Size(max=200)
    private String lastName;
    @Past
    private LocalDate dateOfBirth;
    private List<@Valid AddressDTO> addresses;
    private List<@Valid PersonAvailabilityDTO> personAvailabilities;
}

