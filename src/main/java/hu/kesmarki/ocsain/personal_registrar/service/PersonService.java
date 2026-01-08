package hu.kesmarki.ocsain.personal_registrar.service;

import hu.kesmarki.ocsain.personal_registrar.dto.PersonDTO;
import hu.kesmarki.ocsain.personal_registrar.dto.SearchDTO;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.Person;
import hu.kesmarki.ocsain.personal_registrar.persistence.repository.PersonRepository;
import hu.kesmarki.ocsain.personal_registrar.service.exception.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonDTO get(Long id) throws PersonNotFoundException {
        Optional<Person> person = personRepository.findById(id);
        return person.map(p->modelMapper.map(p, PersonDTO.class))
                .orElseThrow(()-> new PersonNotFoundException(String.format("Person not found for id: %d", id)));
    }

    public PersonDTO save(PersonDTO personDTO){
        LocalDateTime modStamp = LocalDateTime.now();
        Person person = modelMapper.map(personDTO, Person.class);
        person.setModStamp(modStamp);
        person.getPersonAvailabilities().forEach(personAvailability -> {
            personAvailability.setModStamp(modStamp);
            personAvailability.setPerson(person);
        });
        person.getAddresses().forEach(address -> {
            address.setModStamp(modStamp);
            address.setPerson(person);
            address.getAddressAvailabilities().forEach(addressAvailability -> {
                addressAvailability.setModStamp(modStamp);
                addressAvailability.setAddress(address);
            });
        });
        Person savedPerson = personRepository.save(person);
        return modelMapper.map(savedPerson, PersonDTO.class);
    }

    public void delete(Long id){
        personRepository.deleteById(id);
    }

    public List<PersonDTO> find(SearchDTO searchDTO){
       return personRepository.findBySearchDTO(searchDTO).stream().map(person -> modelMapper.map(person, PersonDTO.class)).toList();
    }
}
