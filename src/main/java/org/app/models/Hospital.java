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
@Table(name = "hospitals")
public class Hospital {
    @Id
    @GeneratedValue(generator = "hospital_id_generator",strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "hospital_id_seq_generator",sequenceName = "hospital_id_seq",allocationSize = 1)
    private Long id;
    @Column(length = 30,unique = true)
    private String name;
    @Column(length = 30)
    private String address;
    private String image;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital")
    @ToString.Exclude
    private List<Doctor> doctors = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital")
    @ToString.Exclude
    private List<Patient> patients = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital")
    @ToString.Exclude
    private List<Department> departments = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Appointment> appointments = new ArrayList<>();


}
