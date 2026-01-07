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
@Table(name = "address_availability")
public class AddressAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address_availability_type")
    private AddressAvailabilityType addressAvailabilityType;

    @Column(name = "availability")
    private String availability;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "mod_stamp")
    private LocalDateTime modStamp;

}
