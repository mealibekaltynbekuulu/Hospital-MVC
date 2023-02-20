package org.app.repositories;

import org.app.models.Patient;

import java.util.List;

public interface PatientRepo {
    Patient save(Patient patient);
    List<Patient> getAll();
    void deleteById(Long id);
    Patient getById(Long id);
    void update (Long id, Patient newPatient);
}

