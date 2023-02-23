package org.app.controllers;

import org.app.models.Patient;
import org.app.services.DepartmentService;
import org.app.services.HospitalService;
import org.app.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;

    @Autowired
    public PatientController(PatientService patientService, HospitalService hospitalService, DepartmentService departmentService) {
        this.patientService = patientService;
        this.hospitalService = hospitalService;
        this.departmentService = departmentService;
    }

    @GetMapping("")
    public String getPatients(Model model) {
        model.addAttribute("patients", patientService.getAll());
        return "patients/patients";
    }


    @GetMapping("/new")
    public String hospitalSelection(Model model) {
        model.addAttribute("hospitals", hospitalService.getAll());
        return "patients/hospitalSelection";
    }


    @GetMapping("/{hospitalId}/new")
    public String newPatient(Model model, @PathVariable Long hospitalId) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("departments", departmentService.hospitalDepartments(hospitalId));
        model.addAttribute("hospital", hospitalService.getById(hospitalId));
        return "patients/new";
    }


    @PostMapping("/{hospitalId}/create")
    public String createPatient(@PathVariable Long hospitalId, @ModelAttribute("patient") Patient patient) {
        patientService.save(hospitalId, patient);
        return "redirect:/patients";
    }

    @GetMapping("/{hospitalId}/edit/{id}")
    public String editPatient(@PathVariable Long hospitalId, @PathVariable Long id, Model model) {
        model.addAttribute("patient", patientService.getById(id));
        model.addAttribute("departments", departmentService.hospitalDepartments(hospitalId));
        return "patients/edit";
    }

    @PatchMapping("/{hospitalId}/edit/{id}")
    public String updatePatient(@PathVariable Long hospitalId, @PathVariable Long id, @ModelAttribute("patient") Patient patient) {
        patientService.update(id, patient);
        return "redirect:/patients";
    }


    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deleteById(id);
        return "redirect:/patients";
    }
}
