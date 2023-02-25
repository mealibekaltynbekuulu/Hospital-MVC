package org.app.services.impl;


import lombok.RequiredArgsConstructor;

import org.app.models.Department;


import org.app.repositories.DepartmentRepo;

import org.app.services.DepartmentService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo departmentRepo;


    @Override
    public Department save(Long hospitalId, Department department) {
        return departmentRepo.save(hospitalId, department);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepo.getAll();
    }


    @Override
    public void deleteById(Long id) {
        departmentRepo.deleteById(id);
    }

    @Override
    public Department getById(Long id) {
        return departmentRepo.getById(id);
    }

    @Override
    public void update(Long id, Department newDepartment) {
        departmentRepo.update(id, newDepartment);
    }

    @Override
    public List<Department> hospitalDepartments(Long id) {
        return departmentRepo.hospitalDepartment(id);
    }
}
