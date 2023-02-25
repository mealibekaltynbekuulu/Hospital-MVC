package org.app.models;

import jakarta.persistence.*;
import lombok.*;
import org.app.models.enums.Gender;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="patients")
public class Patient {
    @Id
    @GeneratedValue(generator = "patients_id_generator",strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "patients_id_seq_generator",sequenceName = "patients_id_seq",allocationSize = 1)
    private Long id;
    @Column(name = "first_name",length = 30)
    private String firstName;
    @Column(name = "last_name",length = 30)
    private String lastName;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = {REFRESH,MERGE,DETACH,PERSIST})
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @OneToMany(mappedBy = "patient",fetch = FetchType.LAZY,cascade = ALL)
    @ToString.Exclude
    private List<Appointment> appointments = new ArrayList<>();

    @Transient
    private Long hospitalId;

    @Transient
    private List<Long> departmentsIdList = new ArrayList<>();
}
