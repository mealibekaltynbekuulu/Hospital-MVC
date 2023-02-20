package org.app.controllers;

import org.app.models.Hospital;
import org.app.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("hospitals",hospitalService.getAll());
        return "hospitals/hospitals";
    }

    @GetMapping("/new")
    public String newHospital(Model model){
        model.addAttribute("newHospital",new Hospital());
        return "hospitals/new";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute("newHospital") Hospital hospital){
        hospitalService.save(hospital);
        return "redirect:/hospitals";
    }
}
