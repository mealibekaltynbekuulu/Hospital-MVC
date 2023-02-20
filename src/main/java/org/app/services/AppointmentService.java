package org.app.services;

import org.app.models.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment save(Appointment appointment);
    List<Appointment> getAll();
    void deleteById(Long id);
    Appointment getById(Long id);
    void update (Long id, Appointment newAppointment);
}
