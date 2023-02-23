package org.app.controllers;

import org.app.models.Hospital;
import org.app.services.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("hospital", hospitalService.getById(id));
        return "hospitals/edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("hospital") Hospital newHospital) {
        hospitalService.update(id, newHospital);
        return "redirect:/hospitals";
    }

    @GetMapping("/{id}")
    public String getHospital(@PathVariable("id") Long id,Model model){
        model.addAttribute("hospital",hospitalService.getById(id));
        return "hospitals/hospital";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteHospital(@PathVariable("id") Long id){
        hospitalService.deleteById(id);
        return "redirect:/hospitals";
    }
}
