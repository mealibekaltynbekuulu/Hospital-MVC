package org.app.services;

import org.app.models.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor save(Long hospitalId,Doctor doctor);
    List<Doctor> getAll();
    void deleteById(Long id);
    Doctor getById(Long id);
    void update (Long id, Doctor newDoctor);
    List<Doctor> getDoctorsByDepartmentId(Long id);

    List<Doctor> getDoctorsByHospitalId(Long id);
}
