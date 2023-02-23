package org.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.app.models.Doctor;
import org.app.repositories.DepartmentRepo;
import org.app.repositories.DoctorRepo;
import org.app.repositories.impl.DoctorRepoImpl;
import org.app.services.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    @Override
    public Doctor save(Long hospitalId,Doctor doctor)  {
        return doctorRepo.save(hospitalId,doctor);
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepo.getAll();
    }

    @Override
    public void deleteById(Long id) {
        doctorRepo.deleteById(id);
    }

    @Override
    public Doctor getById(Long id) {
        return doctorRepo.getById(id);
    }

    @Override
    public void update(Long id, Doctor newDoctor) {
        doctorRepo.update(id,newDoctor);
    }

    @Override
    public List<Doctor> getDoctorsByDepartmentId(Long id) {
        return doctorRepo.getDoctorsByDepartmentId(id);
    }
}
