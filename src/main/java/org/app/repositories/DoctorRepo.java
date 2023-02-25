package org.app.repositories;

import org.app.models.Doctor;

import javax.print.Doc;
import java.util.List;

public interface DoctorRepo {
    Doctor save(Long hospitalId,Doctor doctor);
    List<Doctor> getAll();
    void deleteById(Long id);
    Doctor getById(Long id);
    void update (Long id, Doctor newDoctor);
    List<Doctor> getDoctorsByDepartmentId(Long id);

    List<Doctor> getDoctorsByHospitalId(Long id);
}
