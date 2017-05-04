package com.residentevil.services;

import com.residentevil.entities.Role;
import com.residentevil.mappers.ModelParser;
import com.residentevil.models.view.role.RoleChangeView;
import com.residentevil.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelParser modelParser;

    @Override
    public List<RoleChangeView> getAllRoles() {

        List<Role> roles = this.roleRepository.findAll();
        List<RoleChangeView> roleChangeViews = new ArrayList<>();

        for (Role role : roles) {

            RoleChangeView roleChangeView = this.modelParser.convert(role, RoleChangeView.class);
            roleChangeViews.add(roleChangeView);
        }

        return roleChangeViews;
    }
}
