package com.cardealer.controllers;

import com.cardealer.models.binding.sale.SaleReviewModel;
import com.cardealer.models.view.car.CarSelectionView;
import com.cardealer.models.view.customer.CustomerAddSaleView;
import com.cardealer.models.view.sale.ReviewViewModel;
import com.cardealer.models.view.sale.SaleWithCarView;
import com.cardealer.models.view.sale.SalesView;
import com.cardealer.services.CarService;
import com.cardealer.services.CustomerService;
import com.cardealer.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarService carService;

    @GetMapping("")
    public String getSales(Model model) {

        List<SalesView> salesViewList = this.saleService.getAll();
        model.addAttribute("title", "Sales");
        model.addAttribute("sales", salesViewList);

        model.addAttribute("view", "sales/sales-table");
        return "base-layout";
    }

    @PostMapping("")
    public String salesByPercent(Model model,
                                 @RequestParam("percent") Integer percent) {

        return getString(model, percent);
    }

    @GetMapping("/{id}")
    public String getSales(Model model,
                           @PathVariable("id") Long id) {

        SaleWithCarView saleWithCarView = this.saleService.getSaleById(id);
        model.addAttribute("title", "Sale by id");
        model.addAttribute("sale", saleWithCarView);

        model.addAttribute("view", "sales/sale-id-table");
        return "base-layout";
    }

    @GetMapping("/discounted")
    public String getDiscountedSales(Model model) {

        List<SalesView> salesViewList = this.saleService.getAllWithDiscount();

        model.addAttribute("title", "Discounted sales");
        model.addAttribute("sales", salesViewList);

        model.addAttribute("view", "sales/sales-table");
        return "base-layout";
    }

    @PostMapping("/discounted")
    public String discountedSalesByPercent(Model model,
                                           @RequestParam("percent") Integer percent) {

        return getString(model, percent);
    }

    private String getString(Model model,
                             @RequestParam("percent") Integer percent) {

        List<SalesView> salesViewList = this.saleService.getAllSalesByDiscount(percent);
        model.addAttribute("title", "Discounted sales");
        model.addAttribute("sales", salesViewList);

        model.addAttribute("view", "sales/sales-table");
        return "base-layout";
    }

    @GetMapping("add")
    public String getAddSale(Model model) {

        model.addAttribute("title", "Add sale");

        List<CustomerAddSaleView> customerAddSaleViews = this.customerService.getAllCustomersByName();
        List<CarSelectionView> carSelectionViews = this.carService.getAllCarsByMakeAndByModel();

        model.addAttribute("customers", customerAddSaleViews);
        model.addAttribute("cars", carSelectionViews);

        model.addAttribute("view", "sales/sale-add");
        return "base-layout";
    }

    @PostMapping("add")
    public String addSale(@ModelAttribute SaleReviewModel saleReviewModel,
                          RedirectAttributes redirectAttributes) {

        ReviewViewModel reviewViewModel = this.saleService.processSaleReviewModel(saleReviewModel);
        redirectAttributes.addFlashAttribute("sale", reviewViewModel);
        return "redirect:/sales/add/review";
    }

    @GetMapping("add/review")
    public String getAddSaleReview(Model model,
                                   HttpSession session) {

        model.addAttribute("title", "Review add sale");

        ReviewViewModel reviewViewModel = (ReviewViewModel) model.asMap().get("sale");
        model.addAttribute("saleReview", reviewViewModel);
        session.setAttribute("cid", reviewViewModel.getCarId());

        model.addAttribute("view", "sales/sale-add-review");
        return "base-layout";
    }

    @PostMapping("add/review")
    public String finalizeSale(HttpSession session,
                               @ModelAttribute ReviewViewModel reviewViewModel) {

        Long cid = (Long) session.getAttribute("cid");
        this.saleService.addNewSale(reviewViewModel, cid);
        return "redirect:/sales";
    }
}
