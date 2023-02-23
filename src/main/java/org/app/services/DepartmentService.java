package org.app.services;

import org.app.models.Department;

import java.util.List;

public interface DepartmentService {
    Department save(Long hospitalId,Department department);
    List<Department> getAll();
    void deleteById(Long id);
    Department getById(Long id);
    void update (Long id, Department newDepartment);
    List<Department> hospitalDepartments(Long id);
}
