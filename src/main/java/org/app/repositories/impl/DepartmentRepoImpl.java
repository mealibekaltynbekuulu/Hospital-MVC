package org.app.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.app.models.Department;
import org.app.models.Hospital;
import org.app.repositories.DepartmentRepo;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class DepartmentRepoImpl implements DepartmentRepo {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public DepartmentRepoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Department save(Long hospitalId, Department department) {
        try {
            Hospital hospital = entityManager.find(Hospital.class, hospitalId);
            hospital.getDepartments().add(department);
            department.setHospital(hospital);
            entityManager.persist(department);
            entityManager.merge(hospital);
            return department;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = new ArrayList<>();
        try {
            departments = entityManager.createQuery("select d from Department d").getResultList();
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return departments;
    }

    @Override
    public void deleteById(Long id) {
        boolean isDeleted = false;
        try{
            entityManager.createQuery("delete from Department d where d.id = :id").setParameter("id",id).executeUpdate();
            isDeleted = true;
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        System.out.println(isDeleted ? "Department Deleted Successfully!" : "Department was not deleted");
    }

    @Override
    public Department getById(Long id) {
        try {
            Department department = entityManager.find(Department.class, id);
            return department;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Long id, Department newDepartment) {
        boolean isUpdated = false;
        try {
            Department department = entityManager.find(Department.class, id);
            department.setName(newDepartment.getName());
            department.setImage(newDepartment.getImage());
            entityManager.merge(department);
            isUpdated = true;
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(isUpdated ? "Department is updated successfully" : "Department was not updated");
    }

    @Override
    public List<Department> hospitalDepartment(Long hospitalId) {
        List<Department> results = new ArrayList<>();
        try{
            results = entityManager.createQuery("select d from Department d  where d.hospital.id = :id")
                    .setParameter("id",hospitalId).getResultList();
        }catch (HibernateException e){
            System.out.println(e.getMessage());
        }
        return results;
    }
}
