package com.residentevil.controllers;

import com.residentevil.entities.enumerations.Mutation;
import com.residentevil.models.binding.virus.AddVirusModel;
import com.residentevil.models.binding.virus.UpdateVirusModel;
import com.residentevil.models.view.capital.CapitalsAddVirusView;
import com.residentevil.models.view.virus.EditVirusView;
import com.residentevil.models.view.virus.VirusShowView;
import com.residentevil.services.CapitalService;
import com.residentevil.services.VirusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VirusController {

    @Autowired
    private CapitalService capitalService;

    @Autowired
    private VirusService virusService;

    @ModelAttribute(name = "mutationsName")
    private List<String> getMutation() {

        Mutation[] mutationsArray = Mutation.values();
        List<String> mutations = new ArrayList<>();

        for (Mutation mutation : mutationsArray) {

            mutations.add(mutation.toString());
        }

        return mutations;
    }

    @GetMapping("/virus/add")
    public String getAddVirus(Model model,
                              @ModelAttribute AddVirusModel addVirusModel) {

        model.addAttribute("title", "Add virus");
        List<CapitalsAddVirusView> capitalsAddVirusViewList = this.capitalService.getCapitalNames();
        model.addAttribute("capitals", capitalsAddVirusViewList);
        model.addAttribute("view", "virus/virus-add");
        return "base-layout";
    }

    @PostMapping("/virus/add")
    public String addVirus(@Valid @ModelAttribute AddVirusModel addVirusModel,
                           BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {

            List<CapitalsAddVirusView> capitalsAddVirusViewList = this.capitalService.getCapitalNames();
            model.addAttribute("capitals", capitalsAddVirusViewList);

            model.addAttribute("view", "virus/virus-add");
            return "base-layout";
        }

        this.virusService.addVirus(addVirusModel);
        return "redirect:/viruses";
    }

    @GetMapping({"/viruses", "/cures"})
    public String getAllViruses(Model model) {

        model.addAttribute("title", "All viruses");

        List<VirusShowView> virusShowViews = this.virusService.getAllViruses();
        model.addAttribute("viruses", virusShowViews);

        model.addAttribute("view", "virus/viruses-show");
        return "base-layout";
    }

    @GetMapping("/virus/edit/{id}")
    public String getEditVirus(Model model,
                               @PathVariable("id") Long id) {

        model.addAttribute("title", "Edit virus");
        EditVirusView editVirusView = this.virusService.getById(id);
        model.addAttribute("virus", editVirusView);

        model.addAttribute("view", "virus/virus-edit");
        return "base-layout";
    }

    @PostMapping("/virus/edit/{id}")
    public String editVirus(@Valid @ModelAttribute UpdateVirusModel updateVirusModel,
                            @PathVariable("id") Long id,
                            BindingResult bindingResult,
                            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("view", "virus/virus-edit");
            return "base-layout";
        }

        this.virusService.updateVirus(id, updateVirusModel);
        return "redirect:/viruses";
    }

    @GetMapping("/virus/delete/{id}")
    public String getDeleteVirus(Model model,
                                 @PathVariable("id") Long id) {

        model.addAttribute("title", "Delete virus");
        EditVirusView editVirusView = this.virusService.getById(id);
        model.addAttribute("virus", editVirusView);

        model.addAttribute("view", "virus/virus-delete");
        return "base-layout";
    }

    @PostMapping("/virus/delete/{id}")
    public String editVirus(@PathVariable("id") Long id) {

        this.virusService.deleteVirus(id);
        return "redirect:/viruses";
    }
}
