package com.cardealer.controllers;

import com.cardealer.models.view.supplier.SupplierPartsView;
import com.cardealer.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/{param}")
    public String getLocal(Model model,
                           @PathVariable("param") String param) {

        List<SupplierPartsView> supplierPartsViews = this.supplierService.getSuppliers(param);

        model.addAttribute("suppliers", supplierPartsViews);
        model.addAttribute("view", "suppliers/suppliers-table");

        return "base-layout";
    }
}
