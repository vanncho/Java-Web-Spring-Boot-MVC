package com.onlinebusreservation.areas.info.controllers;

import com.google.gson.Gson;
import com.onlinebusreservation.areas.info.models.binding.CompanyInfoEditModel;
import com.onlinebusreservation.areas.info.models.view.CompanyInfoViewModel;
import com.onlinebusreservation.areas.info.services.CompanyInfoService;
import com.onlinebusreservation.interceptors.UserUnreadMessagesInterceptor;
import com.onlinebusreservation.interceptors.ValidBookingCheckInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompanyInfoRestController.class)
@ActiveProfiles("test")
public class CompanyInfoRestControllerTest {

    private static final String GET_URL = "/info/info/view";
    private static final String GET_EDIT_URL = "/info/info/edit";
    private static final String POST_EDIT_URL = "/info/info/edit";

    private static final Integer METHOD_CALL_TIMES = 1;

    private static final long INFO_ID = 1;
    private static final String INFO_NAME = "USA Buslines";
    private static final String INFO_TOWN = "Miami";
    private static final String INFO_ADDRESS = "Somewhere in city of Miami.";
    private static final String INFO_PHONE = "0123456789";

    private static final String INFO_NAME_FAIL = "US";
    private static final String INFO_TOWN_FAIL = "Mi";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CompanyInfoService companyInfoService;

    @MockBean
    private ValidBookingCheckInterceptor bookingCheckInterceptor;

    @MockBean
    private UserUnreadMessagesInterceptor userUnreadMessagesInterceptor;

    @Captor
    private ArgumentCaptor<HttpServletRequest> servletRequestCaptor;

    @Captor
    private ArgumentCaptor<HttpServletResponse> servletResponseCaptor;

    @Captor
    private ArgumentCaptor<Object> objectCaptor;

    @Before
    public void setUp() throws Exception {

        when(this.bookingCheckInterceptor.preHandle(this.servletRequestCaptor.capture(), this.servletResponseCaptor.capture(), this.objectCaptor.capture())).thenReturn(true);
        when(this.userUnreadMessagesInterceptor.preHandle(this.servletRequestCaptor.capture(), this.servletResponseCaptor.capture(), this.objectCaptor.capture())).thenReturn(true);

        CompanyInfoViewModel companyInfo = new CompanyInfoViewModel();
        companyInfo.setId(INFO_ID);
        companyInfo.setCompanyName(INFO_NAME);
        companyInfo.setTown(INFO_TOWN);
        companyInfo.setAddress(INFO_ADDRESS);
        companyInfo.setPhoneNumber(INFO_PHONE);

        when(this.companyInfoService.getCompanyInfo()).thenReturn(companyInfo);

        CompanyInfoEditModel infoEditModel = new CompanyInfoEditModel();
        infoEditModel.setId(INFO_ID);
        infoEditModel.setCompanyName(INFO_NAME);
        infoEditModel.setTown(INFO_TOWN);
        infoEditModel.setAddress(INFO_ADDRESS);
        infoEditModel.setPhoneNumber(INFO_PHONE);

        when(this.companyInfoService.getCompanyInfo(INFO_ID)).thenReturn(infoEditModel);
    }

    @Test
    public void getCompanyInfo_ShouldReturnCompanyInfoViewModel() throws Exception {

        this.mvc
                .perform(get(GET_URL))
                .andExpect(jsonPath("$.id", is((int)INFO_ID)))
                .andExpect(jsonPath("$.companyName", is(INFO_NAME)))
                .andExpect(jsonPath("$.town", is(INFO_TOWN)))
                .andExpect(jsonPath("$.address", is(INFO_ADDRESS)))
                .andExpect(jsonPath("$.phoneNumber", is(INFO_PHONE)));

        verify(this.companyInfoService, times(METHOD_CALL_TIMES)).getCompanyInfo();
        verifyNoMoreInteractions(this.companyInfoService);
    }

    @Test
    public void getEditCompanyInfo_ShouldReturnCompanyInfoEditModel() throws Exception {

        this.mvc
                .perform(get(GET_EDIT_URL+  "/" + INFO_ID))
                .andExpect(jsonPath("$.id", is((int)INFO_ID)))
                .andExpect(jsonPath("$.companyName", is(INFO_NAME)))
                .andExpect(jsonPath("$.town", is(INFO_TOWN)))
                .andExpect(jsonPath("$.address", is(INFO_ADDRESS)))
                .andExpect(jsonPath("$.phoneNumber", is(INFO_PHONE)));

        verify(this.companyInfoService, times(METHOD_CALL_TIMES)).getCompanyInfo(INFO_ID);
        verifyNoMoreInteractions(this.companyInfoService);
    }

    @Test
    public void editCompanyInfoWithCorrectFormData_ShouldUpdateCompanyInfo() throws Exception {

        CompanyInfoEditModel companyInfoEditModel = new CompanyInfoEditModel();
        companyInfoEditModel.setId(INFO_ID);
        companyInfoEditModel.setCompanyName(INFO_NAME);
        companyInfoEditModel.setTown(INFO_TOWN);
        companyInfoEditModel.setAddress(INFO_ADDRESS);
        companyInfoEditModel.setPhoneNumber(INFO_PHONE);

        String json = new Gson().toJson(companyInfoEditModel);

        this.mvc
                .perform(post(POST_EDIT_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void editCompanyInfoWithIncorrectFormData_ShouldFailToUpdate() throws Exception {

        CompanyInfoEditModel companyInfoEditModel = new CompanyInfoEditModel();
        companyInfoEditModel.setId(INFO_ID);
        companyInfoEditModel.setCompanyName(INFO_NAME_FAIL);
        companyInfoEditModel.setTown(INFO_TOWN_FAIL);
        companyInfoEditModel.setAddress(INFO_ADDRESS);
        companyInfoEditModel.setPhoneNumber(INFO_PHONE);

        String json = new Gson().toJson(companyInfoEditModel);

        this.mvc
                .perform(post(POST_EDIT_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

}