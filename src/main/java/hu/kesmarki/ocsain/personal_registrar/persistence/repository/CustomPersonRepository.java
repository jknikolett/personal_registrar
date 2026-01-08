package hu.kesmarki.ocsain.personal_registrar.persistence.repository;

import hu.kesmarki.ocsain.personal_registrar.dto.SearchDTO;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.Person;

import java.util.List;

public interface CustomPersonRepository {

    List<Person> findBySearchDTO(SearchDTO searchDTO);
}
