package com.onlinebusreservation.areas.info.services;

import com.onlinebusreservation.areas.info.models.binding.CompanyInfoEditModel;
import com.onlinebusreservation.areas.info.models.view.CompanyInfoViewModel;

public interface CompanyInfoService {

    CompanyInfoViewModel getCompanyInfo();

    CompanyInfoEditModel getCompanyInfo(Long id);

    void updateInfo(CompanyInfoEditModel companyInfoEditModel);
}
