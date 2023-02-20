package org.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(generator = "doctor_id_generator",strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "doctor_id_seq_generator",sequenceName = "doctor_id_seq",allocationSize = 1)
    private Long id;
    @Column(name = "first_name",length = 30)
    private String firstName;
    @Column(name = "last_name",length = 30)
    private String lastName;
    @Column(length = 30)
    private String position;
    @Column(unique = true)
    private String email;
    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Department> departments = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "doctor")
    @ToString.Exclude
    private List<Appointment> appointments = new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Hospital hospital;
}
