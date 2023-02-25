package org.app.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.app.models.Appointment;
import org.app.models.Department;
import org.app.models.Doctor;
import org.app.repositories.AppointmentRepo;
import org.app.repositories.DepartmentRepo;
import org.app.services.AppointmentService;
import org.app.services.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo departmentRepo;

    @Override
    public Department save(Long hospitalId,Department department) {
        return departmentRepo.save(hospitalId,department);
    }

    @Override
    public List<Department> getAll() {
        return departmentRepo.getAll();
    }


    @Override
    public void deleteById(Long id) {
//        Department department = departmentRepo.getById(id);
//        List<Appointment> appointments = department.getHospital().getAppointments();
//        if (appointments != null) {
//            List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
//            appointmentList.forEach(s->appointmentRepo.deleteById(s.getId()));
//        }
//        List<Department> departments = department.getHospital().getDepartments();
//        departments.removeIf(s->s.getId().equals(id));
//        List<Doctor> doctors = department.getDoctors();
//        doctors.forEach(d->d.getDepartments().removeIf(s->s.getId().equals(id)));
        departmentRepo.deleteById(id);

    }

    @Override
    public Department getById(Long id) {
        return departmentRepo.getById(id);
    }

    @Override
    public void update(Long id, Department newDepartment) {
        departmentRepo.update(id,newDepartment);
    }

    @Override
    public List<Department> hospitalDepartments(Long id) {
        return departmentRepo.hospitalDepartment(id);
    }
}
