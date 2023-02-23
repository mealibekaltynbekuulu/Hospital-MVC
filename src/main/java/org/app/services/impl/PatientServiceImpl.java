package org.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.app.models.Patient;
import org.app.repositories.PatientRepo;
import org.app.services.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    @Override
    public Patient save(Long hospitalId,Patient patient) {
        return patientRepo.save(hospitalId,patient);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepo.getAll();
    }

    @Override
    public void deleteById(Long id) {
        patientRepo.deleteById(id);
    }

    @Override
    public Patient getById(Long id) {
        return patientRepo.getById(id);
    }

    @Override
    public void update(Long id, Patient newPatient) {
        patientRepo.update(id,newPatient);
    }

    @Override
    public List<Patient> getPatientsByDepartmentId(Long id) {
        return  patientRepo.getPatientsByDepartmentId(id);
    }
}
