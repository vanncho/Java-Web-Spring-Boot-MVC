package com.onlinebusreservation.areas.info.controllers;

import com.onlinebusreservation.areas.info.models.binding.CompanyInfoEditModel;
import com.onlinebusreservation.areas.info.models.view.CompanyInfoViewModel;
import com.onlinebusreservation.areas.info.services.CompanyInfoService;
import com.onlinebusreservation.areas.info.validations.ValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/info/info")
public class CompanyInfoRestController {

    private final CompanyInfoService companyInfoService;

    @Autowired
    public CompanyInfoRestController(CompanyInfoService companyInfoService) {
        this.companyInfoService = companyInfoService;
    }

    @GetMapping("/view")
    public ResponseEntity<CompanyInfoViewModel> getCompanyInfo() {

        CompanyInfoViewModel companyInfo = this.companyInfoService.getCompanyInfo();
        ResponseEntity<CompanyInfoViewModel> responseEntity = new ResponseEntity<>(companyInfo, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/edit/{id}")
    public ResponseEntity<CompanyInfoEditModel> getEditCompanyInfo(@PathVariable("id") long id) {

        CompanyInfoEditModel infoEditModel = this.companyInfoService.getCompanyInfo(id);
        ResponseEntity<CompanyInfoEditModel> responseEntity = new ResponseEntity<>(infoEditModel, HttpStatus.OK);
        return responseEntity;
    }

    @PostMapping("/edit")
    public ResponseEntity editCompanyInfo(@Valid @RequestBody CompanyInfoEditModel companyInfoEditModel,
                                          Errors errors) {

        if (errors.hasErrors()) {

            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        this.companyInfoService.updateInfo(companyInfoEditModel);
        return new ResponseEntity(HttpStatus.OK);
    }
}
