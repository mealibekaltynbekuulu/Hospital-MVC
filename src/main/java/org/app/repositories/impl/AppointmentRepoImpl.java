package org.app.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.app.models.Appointment;
import org.app.models.Hospital;
import org.app.repositories.AppointmentRepo;
import org.app.services.DepartmentService;
import org.hibernate.HibernateException;
import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class AppointmentRepoImpl implements AppointmentRepo {
    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public AppointmentRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Appointment save(Appointment appointment) {
        try {
            Hospital hospital = entityManager.find(Hospital.class,appointment.getDoctor().getHospital().getId());
            hospital.getAppointments().add(appointment);
            entityManager.persist(appointment);
            return appointment;
        } catch (PersistentObjectException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Appointment> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        try {
            appointments = entityManager.createQuery("select a from Appointment a", Appointment.class).getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return appointments;
    }

    @Override
    public void deleteById(Long id) {
        try {
            Appointment appointment = entityManager.find(Appointment.class,id);
            entityManager.remove(appointment);
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Appointment getById(Long id) {
        try {
            return entityManager.find(Appointment.class, id);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        try {
            Appointment appointment = entityManager.find(Appointment.class, id);
            appointment.setDoctor(newAppointment.getDoctor());
            appointment.setPatient(newAppointment.getPatient());
            appointment.setDate(newAppointment.getDate());
            entityManager.merge(appointment);
        } catch (HibernateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
