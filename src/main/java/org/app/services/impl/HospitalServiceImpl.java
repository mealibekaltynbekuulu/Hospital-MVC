package org.app.services.impl;

import lombok.RequiredArgsConstructor;
import org.app.models.Hospital;
import org.app.repositories.HospitalRepo;
import org.app.services.HospitalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepo hospitalRepo;
    @Override
    public Hospital save(Hospital hospital) {
        return hospitalRepo.save(hospital);
    }

    @Override
    public List<Hospital> getAll() {
        return hospitalRepo.getAll();
    }

    @Override
    public void deleteById(Long id) {
        hospitalRepo.deleteById(id);
    }

    @Override
    public Hospital getById(Long id) {
        return hospitalRepo.getById(id);
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        hospitalRepo.update(id,newHospital);
    }
}
