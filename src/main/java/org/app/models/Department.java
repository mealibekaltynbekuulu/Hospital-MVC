package org.app.models;

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
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(generator = "department_id_generator", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "department_id_seq_generator", sequenceName = "department_id_seq", allocationSize = 1)
    private Long id;
    @Column(length = 30, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "departments",cascade = {DETACH,MERGE,REFRESH,PERSIST})
    @ToString.Exclude
    private List<Doctor> doctors = new ArrayList<>();
    @ManyToOne(cascade = {MERGE,REFRESH,PERSIST,DETACH})
    private Hospital hospital;

    private String image;
    @Transient
    private Long hospitalId;
}

