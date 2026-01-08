package hu.kesmarki.ocsain.personal_registrar.persistence.repository;

import hu.kesmarki.ocsain.personal_registrar.dto.SearchDTO;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testSaveAndGetPersonWithRelations() {
        Person person = personRepository.save(buildPerson());
        entityManager.flush();
        entityManager.clear();
        List<Address> addresses = person.getAddresses();
        Address address = addresses.get(0);
        List<AddressAvailability> addressAvailabilities = address.getAddressAvailabilities();
        AddressAvailability addressAvailability = addressAvailabilities.get(0);
        address.setAddressAvailabilities(null);
        person.setAddresses(null);
        List<PersonAvailability> personAvailabilities = person.getPersonAvailabilities();
        PersonAvailability personAvailability = personAvailabilities.get(0);
        person.setPersonAvailabilities(null);

        Person fetchedPerson = personRepository.findById(person.getId()).orElse(null);
        assertNotNull(fetchedPerson);
        List<Address> fetchedAddresses = fetchedPerson.getAddresses();
        assertEquals(addresses.size(), fetchedAddresses.size());
        Address fetchedAddress = fetchedAddresses.get(0);
        List<AddressAvailability> fetchedAddressAvailabilities = fetchedAddress.getAddressAvailabilities();
        assertEquals(addressAvailabilities.size(), fetchedAddressAvailabilities.size());
        AddressAvailability fetchedAddressAvailability = addressAvailabilities.get(0);
        fetchedAddress.setAddressAvailabilities(null);
        fetchedPerson.setAddresses(null);
        List<PersonAvailability> fetchedPersonAvailabilities = fetchedPerson.getPersonAvailabilities();
        assertEquals(personAvailabilities.size(), fetchedPersonAvailabilities.size());
        PersonAvailability fetchedPersonAvailability =fetchedPersonAvailabilities.get(0);
        fetchedPerson.setPersonAvailabilities(null);
        assertEquals(addressAvailability, fetchedAddressAvailability);
        assertEquals(personAvailability, fetchedPersonAvailability);
        assertEquals(address, fetchedAddress);
        assertEquals(person, fetchedPerson);
    }

    @Test
    public void testGetListOfPersons() {
        personRepository.save(buildPerson());
        List<Person> personList = personRepository.findAll();
        assertNotNull(personList);
        assertEquals(1, personList.size());
    }

    @Test
    public void testUpdatePerson() {
        Person person = personRepository.save(buildPerson());
        person.setLastName("Kovács");
        personRepository.save(person);
        entityManager.flush();
        entityManager.clear();
        Person updatedPerson = personRepository.findById(person.getId()).orElse(null);
        assertNotNull(updatedPerson);
        assertEquals("Kovács", updatedPerson.getLastName());
    }

    @Test
    public void testDeletePerson() {
        Person person = personRepository.save(buildPerson());
        personRepository.deleteById(person.getId());
        Person deletedPerson = personRepository.findById(person.getId()).orElse(null);
        assertNull(deletedPerson);
    }

    @Test
    public void testFind(){
        personRepository.save(buildPerson());
        SearchDTO searchDTO = SearchDTO.builder()
                .firstName("iko")
                .lastName("cs")
                .fromDateOfBirth(LocalDate.of(2000, Month.DECEMBER, 17))
                .toDateOfBirth(LocalDate.of(2000, Month.DECEMBER, 17))
                .personAvailabilityType(PersonAvailabilityType.EMAIL)
                .personAvailability("test")
                .addressType(AddressType.PERMANENT)
                .zipCode("11")
                .city("Bu")
                .addressLine("rág")
                .addressAvailabilityType(AddressAvailabilityType.PHONE)
                .addressAvailability("2")
        .build();
        List<Person> personList = personRepository.findBySearchDTO(searchDTO);
        assertEquals(1, personList.size());
    }

    private Person buildPerson(){
        Person person = Person.builder()
                .firstName("Nikolett")
                .lastName("Ócsai")
                .dateOfBirth(LocalDate.of(2000, Month.DECEMBER, 17))
                .modStamp(LocalDateTime.now().withNano(0))
                .build();
        person.setPersonAvailabilities(List.of(PersonAvailability.builder()
                .personAvailabilityType(PersonAvailabilityType.EMAIL)
                .availability("test@gmail.com")
                .person(person)
                .modStamp(LocalDateTime.now().withNano(0))
                .build()));
        Address address = Address.builder()
                .addressType(AddressType.PERMANENT)
                .zipCode("1111")
                .city("Budapest")
                .addressLine("Virág u. 6")
                .person(person)
                .modStamp(LocalDateTime.now().withNano(0))
                .build();
        address.setAddressAvailabilities(List.of(AddressAvailability.builder()
                .addressAvailabilityType(AddressAvailabilityType.PHONE)
                .availability("123")
                .address(address)
                .modStamp(LocalDateTime.now().withNano(0))
                .build()));
        person.setAddresses(List.of(address));
        return person;
    }
}
