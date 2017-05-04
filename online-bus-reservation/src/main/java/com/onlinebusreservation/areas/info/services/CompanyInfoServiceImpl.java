package com.onlinebusreservation.areas.info.services;

import com.onlinebusreservation.areas.info.entities.CompanyInfo;
import com.onlinebusreservation.areas.info.models.binding.CompanyInfoEditModel;
import com.onlinebusreservation.areas.info.models.view.CompanyInfoViewModel;
import com.onlinebusreservation.areas.info.repositories.CompanyInfoRepository;
import com.onlinebusreservation.mappers.ModelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    private final CompanyInfoRepository companyInfoRepository;

    private final ModelParser modelParser;

    @Autowired
    public CompanyInfoServiceImpl(CompanyInfoRepository companyInfoRepository, ModelParser modelParser) {

        this.companyInfoRepository = companyInfoRepository;
        this.modelParser = modelParser;
    }


    @Override
    public CompanyInfoViewModel getCompanyInfo() {

        CompanyInfo companyInfo = this.companyInfoRepository.getCompanyInfo();
        CompanyInfoViewModel companyInfoViewModel = this.modelParser.convert(companyInfo, CompanyInfoViewModel.class);
        return companyInfoViewModel;
    }

    @Override
    public CompanyInfoEditModel getCompanyInfo(Long id) {

        CompanyInfo companyInfo = this.companyInfoRepository.findOne(id);
        CompanyInfoEditModel companyInfoEditModel = this.modelParser.convert(companyInfo, CompanyInfoEditModel.class);
        return companyInfoEditModel;
    }

    @Override
    public void updateInfo(CompanyInfoEditModel companyInfoEditModel) {

        this.companyInfoRepository.updateInfo(
                companyInfoEditModel.getId(),
                companyInfoEditModel.getCompanyName(),
                companyInfoEditModel.getAddress(),
                companyInfoEditModel.getTown(),
                companyInfoEditModel.getPhoneNumber());
    }
}
