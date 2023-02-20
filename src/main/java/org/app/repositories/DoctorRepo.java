package org.app.repositories;

import org.app.models.Doctor;

import java.util.List;

public interface DoctorRepo {
    Doctor save(Doctor doctor);
    List<Doctor> getAll();
    void deleteById(Long id);
    Doctor getById(Long id);
    void update (Long id, Doctor newDoctor);
}
