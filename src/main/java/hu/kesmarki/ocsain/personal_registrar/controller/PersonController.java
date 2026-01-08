package hu.kesmarki.ocsain.personal_registrar.controller;

import hu.kesmarki.ocsain.personal_registrar.dto.PersonDTO;
import hu.kesmarki.ocsain.personal_registrar.dto.SearchDTO;
import hu.kesmarki.ocsain.personal_registrar.service.PersonService;
import hu.kesmarki.ocsain.personal_registrar.service.exception.PersonNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonDTO> find(SearchDTO searchDTO){
        return personService.find(searchDTO);
    }

    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO get(@PathVariable("id") Long id) throws PersonNotFoundException {
        return personService.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonDTO save(@RequestBody @Valid PersonDTO personDTO){
        return personService.save(personDTO);
    }
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        personService.delete(id);
    }
}
