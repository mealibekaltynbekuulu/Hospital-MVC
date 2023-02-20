package org.app.repositories;

import org.app.models.Department;

import java.util.List;

public interface DepartmentRepo {
    Department save(Department department);
    List<Department> getAll();
    void deleteById(Long id);
    Department getById(Long id);
    void update (Long id, Department newDepartment);
}
