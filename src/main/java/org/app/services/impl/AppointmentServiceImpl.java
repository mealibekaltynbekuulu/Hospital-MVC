package org.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.app.models.*;
import org.app.repositories.AppointmentRepo;
import org.app.services.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepo appointmentRepo;


    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepo.save(appointment);
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepo.getAll();
    }

    @Override
    public void deleteById(Long id) {
        Appointment existApp = appointmentRepo.getById(id);
        Hospital hospital = existApp.getDoctor().getHospital();
        hospital.getAppointments().remove(existApp);
        for (Doctor doctor : hospital.getDoctors()) {
            doctor.getAppointments().remove(existApp);
        }
        for (Patient patient : hospital.getPatients()) {
            patient.getAppointments().remove(existApp);
        }
        appointmentRepo.deleteById(id);
    }

    @Override
    public Appointment getById(Long id) {
        return appointmentRepo.getById(id);
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        appointmentRepo.update(id,newAppointment);
    }
}
