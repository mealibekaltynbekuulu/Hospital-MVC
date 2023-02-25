package org.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.app.models.Appointment;
import org.app.models.Doctor;
import org.app.models.Hospital;
import org.app.models.Patient;
import org.app.repositories.AppointmentRepo;
import org.app.repositories.DepartmentRepo;
import org.app.repositories.DoctorRepo;
import org.app.repositories.impl.DoctorRepoImpl;
import org.app.services.DoctorService;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final AppointmentRepo appointmentRepo;
    @Override
    public Doctor save(Long hospitalId,Doctor doctor)  {
        return doctorRepo.save(hospitalId,doctor);
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepo.getAll();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Doctor doctor = doctorRepo.getById(id);
        Hospital hospital = doctor.getHospital();
        List<Appointment> appointments = doctor.getAppointments();
        for (Appointment appointment : appointments) {
            appointment.getPatient().setAppointments(null);
            appointment.getDoctor().setAppointments(null);
        }
        hospital.getAppointments().removeAll(appointments);
        for (Appointment appointment : appointments) {
            appointmentRepo.deleteById(appointment.getId());
        }
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
