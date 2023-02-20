package org.app.repositories;

import org.app.models.Appointment;

import java.util.List;

public interface AppointmentRepo {
    Appointment save(Appointment appointment);
    List<Appointment> getAll();
    void deleteById(Long id);
    Appointment getById(Long id);
    void update (Long id, Appointment newAppointment);
}
