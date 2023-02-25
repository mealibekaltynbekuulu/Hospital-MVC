package org.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.app.models.Appointment;
import org.app.models.Hospital;
import org.app.models.Patient;
import org.app.repositories.AppointmentRepo;
import org.app.repositories.PatientRepo;
import org.app.services.PatientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;

    @Override
    public Patient save(Long hospitalId, Patient patient) {
        return patientRepo.save(hospitalId, patient);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepo.getAll();
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        Patient patient = patientRepo.getById(id);
        Hospital hospital = patient.getHospital();
        List<Appointment> appointments = patient.getAppointments();
        for (Appointment appointment : appointments) {
            appointment.getPatient().setAppointments(null);
            appointment.getDoctor().setAppointments(null);
        }
        hospital.getAppointments().removeAll(appointments);
        for (Appointment appointment : appointments) {
            appointmentRepo.deleteById(appointment.getId());
        }
        patientRepo.deleteById(id);
    }

    @Override
    public Patient getById(Long id) {
        return patientRepo.getById(id);
    }

    @Override
    public void update(Long id, Patient newPatient) {
        patientRepo.update(id, newPatient);
    }

    @Override
    public List<Patient> getPatientsByHospitalId(Long id) {
        return patientRepo.getPatientsByHospitalId(id);
    }

    @Override
    public List<Patient> getPatientsByDepartmentId(Long id) {
        return patientRepo.getpatientsByDepartmentId(id);
    }
}
