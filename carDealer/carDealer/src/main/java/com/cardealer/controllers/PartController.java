package com.cardealer.controllers;

import com.cardealer.constants.Messages;
import com.cardealer.models.binding.part.PartAddModel;
import com.cardealer.models.view.part.PartListView;
import com.cardealer.models.view.supplier.SupplierAddPartView;
import com.cardealer.services.PartService;
import com.cardealer.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/parts")
public class PartController {

    @Autowired
    private PartService partService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/add")
    public String getAddPart(Model model) {

        model.addAttribute("title", "Add new part");

        List<SupplierAddPartView> suppliers = this.supplierService.findAll();
        model.addAttribute("suppliers", suppliers);

        model.addAttribute("view", "parts/part-add");
        return "base-layout";
    }

    @PostMapping("add")
    public String addPart(@ModelAttribute PartAddModel partAddModel) {

        this.partService.addNewPart(partAddModel);
        return "redirect:/parts";
    }

    @GetMapping("")
    public String getAllParts(Model model) {

        model.addAttribute("title", "Parts");

        List<PartListView> parts = this.partService.getAll();
        model.addAttribute("parts", parts);

        model.addAttribute("view", "/parts/parts-table");
        return "base-layout";
    }

    @PostMapping("")
    public String getAddNewPart() {

        return "redirect:/parts/part-add";
    }

    @GetMapping("/edit/{id}")
    public String getEditPart(Model model,
                              @PathVariable("id") Long id) {

        model.addAttribute("title", "Edit part");

        PartListView partListView = this.partService.getById(id);
        model.addAttribute("part", partListView);

        model.addAttribute("view", "parts/part-edit");
        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editPart(@PathVariable("id") Long id,
                           @ModelAttribute PartListView partListView) {

        this.partService.edit(partListView, id);
        return "redirect:/parts";
    }

    @GetMapping("/delete/{id}")
    public String getDeletePart(Model model,
                              @PathVariable("id") Long id) {
        PartListView partListView = this.partService.getById(id);

        List<String> errors = new ArrayList<>();
        errors.add(String.format(Messages.DELETE_WARNING, partListView.getName()));
        model.addAttribute("errors", errors);

        model.addAttribute("title", "Delete part");

        model.addAttribute("part", partListView);

        model.addAttribute("view", "parts/part-delete");
        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deletePart(@PathVariable("id") Long id) {

        this.partService.delete(id);
        return "redirect:/parts/parts";
    }
}
