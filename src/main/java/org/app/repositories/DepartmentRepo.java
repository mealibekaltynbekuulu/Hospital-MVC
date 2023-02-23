package org.app.repositories;

import org.app.models.Department;
import org.app.models.Doctor;

import javax.print.Doc;
import java.util.List;

public interface DepartmentRepo {
    Department save(Long hospitalId,Department department);
    List<Department> getAll();
    void deleteById(Long id);
    Department getById(Long id);
    void update (Long id, Department newDepartment);
    List<Department> hospitalDepartment(Long hospitalId);


}
