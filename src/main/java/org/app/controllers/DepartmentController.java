package org.app.controllers;

import org.app.models.Department;
import org.app.services.DepartmentService;
import org.app.services.DoctorService;
import org.app.services.HospitalService;
import org.app.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public DepartmentController(DepartmentService departmentService, HospitalService hospitalService, PatientService patientService, DoctorService doctorService) {
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("")
    public String departments(Model model){
        model.addAttribute("departments",departmentService.getAll());
        return "departments/departments";
    }

    @GetMapping("/new")
    public String newDepartment(Model model){
        model.addAttribute("department",new Department());
        model.addAttribute("hospitals",hospitalService.getAll());
        return "departments/new";
    }

    @PostMapping("/create")
    public String createDepartment(@ModelAttribute("department") Department department){
        departmentService.save(department.getHospitalId(),department);
        return "redirect:/departments";
    }

    @GetMapping("/edit/{id}")
    public String editDepartment(@PathVariable("id") Long id,Model model){
        model.addAttribute("department",departmentService.getById(id));
        model.addAttribute("hospitals",hospitalService.getAll());
        return "departments/edit";
    }

    @PatchMapping("/edit/{id}")
    public  String editDepartment(@PathVariable("id") Long id,
                                  @ModelAttribute("department") Department department){
        departmentService.update(id,department);
        return "redirect:/departments";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable("id") Long id){
        departmentService.deleteById(id);
        return "redirect:/departments";
    }

    @GetMapping("/h/{hospitalId}")
    public String getHospitalDepartments(Model model,@PathVariable("hospitalId") Long hospitalId){
        model.addAttribute("departments",departmentService.hospitalDepartments(hospitalId));
        model.addAttribute("hospital",hospitalService.getById(hospitalId));
        return "departments/hospitalDepartments";
    }



    @GetMapping("/{id}")
    public String getDepartment(@PathVariable("id") Long id,Model model){
        model.addAttribute("department",departmentService.getById(id));
        model.addAttribute("patients",patientService.getPatientsByDepartmentId(id));
        model.addAttribute("doctors",doctorService.getDoctorsByDepartmentId(id));
        return "departments/department";
    }

}
