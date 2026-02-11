package hu.kesmarki.ocsain.personal_registrar.service;

import hu.kesmarki.ocsain.personal_registrar.dto.AddressDTO;
import hu.kesmarki.ocsain.personal_registrar.dto.PersonDTO;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.Address;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressType;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.Person;
import hu.kesmarki.ocsain.personal_registrar.persistence.repository.PersonRepository;
import hu.kesmarki.ocsain.personal_registrar.service.exception.PersonNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PersonServiceTest {

    @MockitoBean
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Captor
    ArgumentCaptor<Person> personArgumentCaptor;

    @SneakyThrows
    @Test
    public void getByIdWhenExists(){
        given(personRepository.findById(1L)).willReturn(Optional.of(Person.builder().build()));
        PersonDTO personDTO = personService.get(1L);
        assertNotNull(personDTO);
    }
    @Test
    public void getByIdWhenNotExists(){
        given(personRepository.findById(1L)).willReturn(Optional.empty());
        assertThrows(PersonNotFoundException.class, ()->personService.get(1L));
    }

    @Test
    public void save(){
        AddressDTO addressDTO = AddressDTO.builder()
                .addressType(AddressType.PERMANENT)
                .addressLine("test cím")
                .city("Város")
                .zipCode("1111")
                .build();
        PersonDTO personDTO = PersonDTO.builder()
                .firstName("Kis")
                .lastName("Virág")
                .addresses(List.of(addressDTO))
                .build();
        given(personRepository.save(any())).willReturn(Person.builder().build());
        personService.save(personDTO);
        verify(personRepository).save(personArgumentCaptor.capture());
        Person person = personArgumentCaptor.getValue();
        assertNotNull(person.getModStamp());
        assertNotNull(person.getAddresses());
        assertEquals(1, person.getAddresses().size());
        Address address = person.getAddresses().get(0);
        assertNotNull(address.getModStamp());
        assertNotNull(address.getPerson());
        assertEquals(person, address.getPerson());

    }
}
