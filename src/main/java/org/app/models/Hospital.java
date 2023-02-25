package org.app.models;

import com.sun.istack.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

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
    @Column(length = 100,unique = true)
    @NotNull
    private String name;
    @Column(length = 100)
    private String address;
    private String image;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital",cascade = ALL)
    @ToString.Exclude
    private List<Doctor> doctors = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital",cascade = ALL)
    @ToString.Exclude
    private List<Patient> patients = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital",cascade = ALL)
    @ToString.Exclude
    private List<Department> departments = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,cascade = ALL)
    @ToString.Exclude
    private List<Appointment> appointments = new ArrayList<>();


}
