package org.app.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.app.models.Hospital;
import org.app.repositories.HospitalRepo;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class
HospitalRepoImpl implements HospitalRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public HospitalRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    @Override
    public Hospital save(Hospital hospital) {
        try {
            entityManager.persist(hospital);
            return hospital;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Hospital> getAll() {
        List<Hospital> hospitals = new ArrayList<>();
        try {
            hospitals = entityManager.createQuery("select h from Hospital h").getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return hospitals;
    }

    @Override
    public void deleteById(Long id) {
        boolean isDeleted = false;
        try {
            entityManager.createQuery("delete from Hospital h where  h.id = :id")
                    .setParameter("id",id).executeUpdate();
            isDeleted = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(isDeleted ? "Hospital Deleted Successfully" : "Hospital was not deleted");
    }

    @Override
    public Hospital getById(Long id) {
        try {
            Hospital hospital = entityManager.find(Hospital.class, id);
            return hospital;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        boolean isUpdated = false;
        try {
            Hospital hospital = entityManager.find(Hospital.class, id);
            hospital.setName(newHospital.getName());
            hospital.setAddress(newHospital.getAddress());
            hospital.setImage(newHospital.getImage());
            entityManager.merge(hospital);
            isUpdated = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(isUpdated ? "Hospital is updated successfully" : "Hospital was not updated");
    }
}
