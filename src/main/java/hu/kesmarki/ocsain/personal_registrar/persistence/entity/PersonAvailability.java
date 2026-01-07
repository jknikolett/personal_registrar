package hu.kesmarki.ocsain.personal_registrar.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_availability")
public class PersonAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "person_availability_type")
    private PersonAvailabilityType personAvailabilityType;

    @Column(name = "availability")
    private String availability;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "mod_stamp")
    private LocalDateTime modStamp;
}
