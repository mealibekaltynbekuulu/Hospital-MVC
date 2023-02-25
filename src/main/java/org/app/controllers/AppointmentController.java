package org.app.controllers;

import jakarta.transaction.Transactional;
import org.app.models.Appointment;
import org.app.models.Department;
import org.app.models.Doctor;
import org.app.models.Patient;
import org.app.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DepartmentService departmentService;
    private final PatientService patientService;
    private final HospitalService hospitalService;

    private final DoctorService doctorService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, DepartmentService departmentService, PatientService patientService, DoctorController doctorController, HospitalService hospitalService, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.departmentService = departmentService;
        this.patientService = patientService;
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
    }

    @GetMapping("/new")
    public String hospitalSelection(Model model) {
        model.addAttribute("hospitals", hospitalService.getAll());
        return "appointments/hospitalSelection";
    }


    @GetMapping("/new/{hId}")
    public String departmentSelection(@PathVariable Long hId, Model model) {
        model.addAttribute("departments", departmentService.hospitalDepartments(hId));
        model.addAttribute(hId);
        return "appointments/departmentSelection";
    }

    @GetMapping("/new/{hId}/{dId}")
    public String newAppointment(@PathVariable Long hId, @PathVariable Long dId, Model model) {
        Appointment appointment = new Appointment();
        appointment.setDepartment(departmentService.getById(dId));
        model.addAttribute("appointment", appointment);
        model.addAttribute("patients", patientService.getPatientsByHospitalId(hId));
        model.addAttribute("doctors", doctorService.getDoctorsByDepartmentId(dId));
        return "appointments/new";
    }

    @Transactional
    @PostMapping("/create/{hId}/{dId}")
    public String createAppointment(@PathVariable Long hId, @PathVariable Long dId, Model model, @ModelAttribute("appointment") Appointment appointment) {
        Patient patient = patientService.getById(appointment.getPatientId());

        Doctor doctor = doctorService.getById(appointment.getDoctorId());
        Department department = departmentService.getById(dId);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDepartment(department);
        appointmentService.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("")
    public String getAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAll());
        return "appointments/appointments";
    }


    @GetMapping("/{hId}/{dId}/edit/{id}")
    public String editAppointment(Model model, @PathVariable Long id, @PathVariable Long hId, @PathVariable Long dId) {
        Appointment appointment = appointmentService.getById(id);
        model.addAttribute("appointment", appointment);
        model.addAttribute("doctors", doctorService.getDoctorsByDepartmentId(dId));
        model.addAttribute("patients", patientService.getPatientsByHospitalId(hId));
        return "appointments/edit";
    }


    @PatchMapping("/{hId}/edit/{id}")
    public String updateAppointment(@ModelAttribute("appointment") Appointment appointment, @PathVariable Long hId,
                                    @PathVariable Long id) {

        appointment.setPatient(patientService.getById(appointment.getPatientId()));
        appointment.setDoctor(doctorService.getById(appointment.getDoctorId()));
        appointmentService.update(id, appointment);
        return "redirect:/appointments";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return "redirect:/appointments";
    }
}
