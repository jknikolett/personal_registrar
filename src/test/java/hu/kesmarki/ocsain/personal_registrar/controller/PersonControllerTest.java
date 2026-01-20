package hu.kesmarki.ocsain.personal_registrar.controller;

import hu.kesmarki.ocsain.personal_registrar.dto.AddressDTO;
import hu.kesmarki.ocsain.personal_registrar.dto.PersonDTO;
import hu.kesmarki.ocsain.personal_registrar.dto.SearchDTO;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.AddressType;
import hu.kesmarki.ocsain.personal_registrar.service.PersonService;
import hu.kesmarki.ocsain.personal_registrar.service.exception.PersonNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class PersonControllerTest {
    @MockitoBean
    private PersonService personService;
    @Autowired
    private RestTestClient restTestClient;
    @SneakyThrows
    @Test
    public void getByIdWhenExists() {
        given(personService.get(1L)).willReturn(PersonDTO.builder().lastName("Virág").build());

        restTestClient.get().uri("/person/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PersonDTO.class)
                .isEqualTo(PersonDTO.builder().lastName("Virág").build());

    }

    @SneakyThrows
    @Test
    public void getByIdWhenNotExists(){
        given(personService.get(1L)).willThrow(new PersonNotFoundException("Person not found for id: 1"));

        restTestClient.get().uri("/person/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Person not found for id: 1");
    }

    @Test
    public void getAll(){
        given(personService.find(SearchDTO.builder().build())).willReturn(List.of(PersonDTO.builder().build()));
        List<PersonDTO> responseBody = restTestClient.get().uri("/person")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<PersonDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertEquals(1, responseBody.size());
    }

    @Test
    public void getByFilter(){
        given(personService.find(SearchDTO.builder().lastName("Vi").build())).willReturn(List.of(PersonDTO.builder().build()));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/person")
                .queryParam("lastName", "Vi")
                .build();
        List<PersonDTO> responseBody = restTestClient.get().uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<PersonDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertEquals(1, responseBody.size());
    }
    @Test
    public void getByFilterNoResult(){
        given(personService.find(SearchDTO.builder().lastName("Vi").build())).willReturn(List.of());
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/person")
                .queryParam("lastName", "Vi")
                .build();
        List<PersonDTO> responseBody = restTestClient.get().uri(uriComponents.toUri())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<PersonDTO>>() {})
                .returnResult()
                .getResponseBody();

        assertEquals(0, responseBody.size());
    }

    @Test
    public void savePerson(){
        PersonDTO personDTO = PersonDTO.builder()
                .lastName("Virág")
                .firstName("Szép")
                .build();
        restTestClient.post()
                .uri("/person")
                .body(personDTO)
                .exchange()
                .expectStatus().isOk();
        verify(personService).save(personDTO);
    }

    @Test
    public void saveNotValidPerson(){
        PersonDTO personDTO = PersonDTO.builder()
                .firstName("Szép")
                .build();
        String responseBody = restTestClient.post()
                .uri("/person")
                .body(personDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .returnResult().getResponseBody();
        assertTrue(responseBody.contains("lastName"));
    }

    @Test
    public void savePersonWithInvalidAddress(){
        AddressDTO addressDTO = AddressDTO.builder()
                .addressType(AddressType.PERMANENT)
                .addressLine("test cím")
                .city("Város")
                .zipCode("1111")
                .build();
        PersonDTO personDTO = PersonDTO.builder()
                .lastName("Virág")
                .firstName("Szép")
                .addresses(List.of(addressDTO, addressDTO))
                .build();

        String responseBody = restTestClient.post()
                .uri("/person")
                .body(personDTO)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .returnResult().getResponseBody();

        assertTrue(responseBody.contains("Invalid addresses"));

    }

    @Test
    public void deletePerson(){
        restTestClient.delete().uri("/person/1")
                .exchange()
                .expectStatus().isOk();
        verify(personService).delete(1L);
    }
}
