package com.onlinebusreservation.areas.user.services;

import com.onlinebusreservation.areas.user.entities.Role;
import com.onlinebusreservation.areas.user.model.view.RoleViewModel;
import com.onlinebusreservation.areas.user.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleViewModel> getRoles() {

        List<Role> roles = this.roleRepository.findAll();
        List<RoleViewModel> roleViewModels = new ArrayList<>();

        for (Role role : roles) {

            RoleViewModel roleViewModel = new RoleViewModel();
            roleViewModel.setAuthority(role.getAuthority().substring(5));
            roleViewModels.add(roleViewModel);
        }

        return roleViewModels;
    }
}
