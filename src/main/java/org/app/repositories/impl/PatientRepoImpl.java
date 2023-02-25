package org.app.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.app.models.Appointment;
import org.app.models.Department;
import org.app.models.Hospital;
import org.app.models.Patient;
import org.app.repositories.PatientRepo;
import org.app.services.HospitalService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class PatientRepoImpl implements PatientRepo {
    @PersistenceContext
    private final EntityManager entityManager;
    private final HospitalService hospitalService;

    @Autowired
    public PatientRepoImpl(EntityManager entityManager, HospitalService hospitalService) {
        this.entityManager = entityManager;
        this.hospitalService = hospitalService;
    }

    @Override
    public Patient save(Long hospitalId, Patient patient) {
        try {
            Hospital hospital = entityManager.find(Hospital.class, hospitalId);
            patient.setHospital(hospital);
            entityManager.persist(patient);
            return patient;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();
        try {
            patients = entityManager.createQuery("select p from Patient p").getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return patients;
    }

    @Override
    public void deleteById(Long id) {
        boolean isDeleted = false;
        try {
            entityManager.createQuery("delete from Patient p where p.id = :id")
                    .setParameter("id", id).executeUpdate();
            isDeleted = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(isDeleted ? "Patient deleted successfully" : "Patient was not deleted");
    }

    @Override
    public Patient getById(Long id) {
        try {
            Patient patient = entityManager.find(Patient.class, id);
            return patient;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Long id, Patient newPatient) {
        try {
            Patient patient = entityManager.find(Patient.class, id);
            patient.setFirstName(newPatient.getFirstName());
            patient.setLastName(newPatient.getLastName());
            patient.setEmail(newPatient.getEmail());
            patient.setPhoneNumber(newPatient.getPhoneNumber());
            patient.setHospital(hospitalService.getById(newPatient.getHospitalId()));
            patient.setGender(newPatient.getGender());


        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Patient> getPatientsByHospitalId(Long id) {
        try {
            return entityManager.createQuery("select p from Patient p join  p.hospital h where h.id=:id", Patient.class)
                    .setParameter("id", id).getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Patient> getpatientsByDepartmentId(Long id) {
        return entityManager.createQuery("select p from Patient p join p.appointments a join a.department d where d.id=:id", Patient.class)
                .setParameter("id",id).getResultList();
    }
}
