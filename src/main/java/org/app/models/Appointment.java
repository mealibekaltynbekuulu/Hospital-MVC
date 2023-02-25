package org.app.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(generator = "appointment_id_generator", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "appointment_id_seq_generator", sequenceName = "appointment_id_seq", allocationSize = 1)
    private Long id;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {REFRESH,PERSIST,MERGE, DETACH})
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {REFRESH,PERSIST,MERGE, DETACH})
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(cascade = {DETACH, PERSIST, REFRESH, MERGE}, fetch = FetchType.EAGER)
    private Department department;


    @Transient
    private Long departmentId;

    @Transient
    private Long doctorId;

    @Transient
    private Long patientId;
    @Transient
    private String dateString;
}
