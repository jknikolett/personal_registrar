package hu.kesmarki.ocsain.personal_registrar.persistence.repository;

import hu.kesmarki.ocsain.personal_registrar.dto.SearchDTO;
import hu.kesmarki.ocsain.personal_registrar.persistence.entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomPersonRepositoryImpl implements CustomPersonRepository{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Person> findBySearchDTO(SearchDTO searchDTO) {
        QueryStringWithParams queryStringWithParams = buildQuery(searchDTO);
        TypedQuery<Person> query = entityManager.createQuery(queryStringWithParams.queryString, Person.class);
        queryStringWithParams.params.forEach(query::setParameter);
        return query.getResultList();
    }

    private QueryStringWithParams buildQuery(SearchDTO searchDTO){
        StringBuilder queryString = new StringBuilder("""
                SELECT DISTINCT p FROM Person p
                INNER JOIN p.addresses a
                INNER JOIN p.personAvailabilities pa
                INNER JOIN a.addressAvailabilities aa
                WHERE 1=1
                """);
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(searchDTO.getFirstName())){
            queryString.append(" AND LOWER(p.firstName) LIKE LOWER(:firstName) ");
            params.put("firstName", wrapLike(searchDTO.getFirstName()));
        }
        if(StringUtils.isNotBlank(searchDTO.getLastName())){
            queryString.append(" AND LOWER(p.lastName) LIKE LOWER(:lastName) ");
            params.put("lastName", wrapLike(searchDTO.getLastName()));
        }
        if(searchDTO.getFromDateOfBirth() != null){
            queryString.append(" AND p.dateOfBirth >= :fromDateOfBirth ");
            params.put("fromDateOfBirth", searchDTO.getFromDateOfBirth());
        }
        if(searchDTO.getToDateOfBirth() != null){
            queryString.append(" AND p.dateOfBirth <= :toDateOfBirth ");
            params.put("toDateOfBirth", searchDTO.getToDateOfBirth());
        }
        if(searchDTO.getPersonAvailabilityType() != null){
            queryString.append(" AND pa.personAvailabilityType = :personAvailabilityType ");
            params.put("personAvailabilityType", searchDTO.getPersonAvailabilityType());
        }
        if(StringUtils.isNotBlank(searchDTO.getPersonAvailability())){
            queryString.append(" AND LOWER(pa.availability) LIKE LOWER(:personAvailability) ");
            params.put("personAvailability", wrapLike(searchDTO.getPersonAvailability()));
        }
        if(searchDTO.getAddressType() != null){
            queryString.append(" AND a.addressType = :addressType ");
            params.put("addressType", searchDTO.getAddressType());
        }
        if(StringUtils.isNotBlank(searchDTO.getZipCode())){
            queryString.append(" AND LOWER(a.zipCode) LIKE LOWER(:zipCode) ");
            params.put("zipCode", wrapLike(searchDTO.getZipCode()));
        }
        if(StringUtils.isNotBlank(searchDTO.getCity())){
            queryString.append(" AND LOWER(a.city) LIKE LOWER(:city) ");
            params.put("city", wrapLike(searchDTO.getCity()));
        }
        if(StringUtils.isNotBlank(searchDTO.getAddressLine())){
            queryString.append(" AND LOWER(a.addressLine) LIKE LOWER(:addressLine) ");
            params.put("addressLine", wrapLike(searchDTO.getAddressLine()));
        }
        if(searchDTO.getAddressAvailabilityType() != null){
            queryString.append(" AND aa.addressAvailabilityType = :addressAvailabilityType ");
            params.put("addressAvailabilityType", searchDTO.getAddressAvailabilityType());
        }
        if(StringUtils.isNotBlank(searchDTO.getAddressAvailability())){
            queryString.append(" AND LOWER(aa.availability) LIKE LOWER(:addressAvailability) ");
            params.put("addressAvailability", wrapLike(searchDTO.getAddressAvailability()));
        }
        return new QueryStringWithParams(queryString.toString(), params);
    }

    private String wrapLike(String s){
        return "%"+s+"%";
    }
    @AllArgsConstructor
    private static class QueryStringWithParams{
        String queryString;
        Map<String, Object> params;

    }
}
