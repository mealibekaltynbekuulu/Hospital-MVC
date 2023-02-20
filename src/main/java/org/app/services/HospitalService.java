package org.app.services;

import org.app.models.Hospital;

import java.util.List;

public interface HospitalService {
    Hospital save(Hospital hospital);
    List<Hospital> getAll();
    void deleteById(Long id);
    Hospital getById(Long id);
    void update (Long id, Hospital newHospital);
}
