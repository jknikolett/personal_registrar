package hu.kesmarki.ocsain.personal_registrar.persistence.repository;

import hu.kesmarki.ocsain.personal_registrar.persistence.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
