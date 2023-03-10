package org.app.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.app.models.Department;
import org.app.models.Doctor;
import org.app.models.Hospital;
import org.app.models.Patient;
import org.app.repositories.DoctorRepo;
import org.app.services.DepartmentService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class DoctorRepoImpl implements DoctorRepo {

    @PersistenceContext
    private final EntityManager entityManager;
    private final DepartmentService departmentService;

    @Autowired
    public DoctorRepoImpl(EntityManager entityManager, DepartmentService departmentService) {
        this.entityManager = entityManager;
        this.departmentService = departmentService;
    }

    @Override
    public Doctor save(Long hospitalId, Doctor doctor) {
        try {
            List<Department> departments = new ArrayList<>();
            for (Long id : doctor.getDepartmentsIdList()) {
                Department department = entityManager.find(Department.class, id);
                departments.add(department);
            }
            doctor.setHospital(entityManager.find(Hospital.class, hospitalId));
            doctor.setDepartments(departments);
            entityManager.persist(doctor);
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> list = new ArrayList<>();
        try {
            list = entityManager.createQuery("select d from Doctor d",Doctor.class).getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void deleteById(Long id) {
        boolean isDeleted = false;
        try {
            entityManager.createQuery("delete from Doctor d where d.id = :id")
                    .setParameter("id",id).executeUpdate();
            isDeleted = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(isDeleted ? "Doctor deleted successfully" : "Doctor was not deleted");
    }

    @Override
    public Doctor getById(Long id) {
        try {
            Doctor doctor = entityManager.find(Doctor.class, id);
            return doctor;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Long id, Doctor newDoctor) {
        boolean isUpdated = false;
        try {
            Doctor doctor = entityManager.find(Doctor.class, id);
            doctor.setFirstName(newDoctor.getFirstName());
            doctor.setLastName(newDoctor.getLastName());
            doctor.setEmail(newDoctor.getEmail());
            doctor.setPosition(newDoctor.getPosition());
            for (Long dId : newDoctor.getDepartmentsIdList()) {
                Department department = departmentService.getById(dId);
                newDoctor.getDepartments().add(department);
            }
            doctor.setDepartments(newDoctor.getDepartments());
            entityManager.merge(doctor);
            isUpdated = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(isUpdated ? "Doctor updated successfully" : "Doctor was not updated");
    }

    @Override
    public List<Doctor> getDoctorsByDepartmentId(Long id) {
        try {
            return entityManager.createQuery("select d from Doctor d join d.departments h where h.id=:id", Doctor.class)
                    .setParameter("id", id).getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Doctor> getDoctorsByHospitalId(Long id) {
        List<Doctor> results = new ArrayList<>();
        try{
            results = entityManager.createQuery("select d from Doctor d join d.departments x join x.hospital h where h.id = :id",Doctor.class)
                    .setParameter("id",id).getResultList();
        }catch (HibernateException e){
            throw new RuntimeException(e.getMessage());
        }
        return results;
    }
}
