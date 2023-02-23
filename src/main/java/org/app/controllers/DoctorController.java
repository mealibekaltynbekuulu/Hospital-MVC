package org.app.controllers;

import org.app.models.Doctor;
import org.app.services.DepartmentService;
import org.app.services.DoctorService;
import org.app.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;

    @Autowired
    public DoctorController(DoctorService doctorService, DepartmentService departmentService, HospitalService hospitalService) {
        this.doctorService = doctorService;
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
    }

    @GetMapping("")
    public String getDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAll());
        return "doctors/doctors";
    }

    @GetMapping("/{hospitalId}/new")
    public String newDoctor(Model model, @PathVariable Long hospitalId) {
        Doctor doctor = new Doctor();
        model.addAttribute("newDoctor", doctor);
        model.addAttribute("hospital", hospitalService.getById(hospitalId));
        model.addAttribute("departments", departmentService.hospitalDepartments(hospitalId));
        return "doctors/new";
    }


    @PostMapping("/{hospitalId}/create")
    public String createDoctor(@ModelAttribute("newDoctor") Doctor doctor, @PathVariable Long hospitalId) {
        doctorService.save(hospitalId, doctor);
        return "redirect:/doctors";
    }


    @GetMapping("/{hospitalId}/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model, @PathVariable Long hospitalId) {
        Doctor doctor = doctorService.getById(id);
        model.addAttribute("doctor", doctor);
        model.addAttribute("departments", departmentService.hospitalDepartments(hospitalId));
        return "doctors/edit";
    }


    @PatchMapping("/{hospitalId}/edit/{id}")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor, @PathVariable Long id) {
        doctorService.update(id, doctor);
        return "redirect:/doctors";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteById(id);
        return "redirect:/doctors";
    }


    @GetMapping("/new")
    public String hospitalSelection(Model model){
        model.addAttribute("hospitals",hospitalService.getAll());
        return "doctors/hospitalSelection";
    }
}
