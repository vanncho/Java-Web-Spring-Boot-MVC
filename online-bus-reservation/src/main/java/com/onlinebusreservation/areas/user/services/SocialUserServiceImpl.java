package com.onlinebusreservation.areas.user.services;

import com.onlinebusreservation.areas.user.entities.Role;
import com.onlinebusreservation.areas.user.entities.SocialUser;
import com.onlinebusreservation.areas.user.entities.enumerations.SocialProvider;
import com.onlinebusreservation.areas.user.repositories.BasicUserRepository;
import com.onlinebusreservation.areas.user.repositories.RoleRepository;
import com.onlinebusreservation.areas.user.repositories.SocialUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Service;

@Service
public class SocialUserServiceImpl implements SocialUserService {

    private final SocialUserRepository socialUserRepository;

    private final BasicUserRepository basicUserRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public SocialUserServiceImpl(SocialUserRepository socialUserRepository,
                                 BasicUserRepository basicUserRepository,
                                 RoleRepository roleRepository) {

        this.socialUserRepository = socialUserRepository;
        this.basicUserRepository = basicUserRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void registerOrLogin(User facebook) {

        String email = facebook.getEmail();
        SocialUser socialUser = this.socialUserRepository.findOneByUsername(email);

        if (socialUser == null) {

            socialUser = this.registerUser(email);
        }

        this.loginUser(socialUser);
    }

    private SocialUser registerUser(String email) {

        SocialUser user = new SocialUser();
        com.onlinebusreservation.areas.user.entities.User basicUser = this.basicUserRepository.findOneByEmail(email);

        user.setUsername(email);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setSocialProvider(SocialProvider.FACEBOOK);
        user.setFullName(basicUser.getFullName());

        Role role = this.roleRepository.findOne(2L);
        user.getAuthorities().add(role);

        this.socialUserRepository.save(user);

        return user;
    }

    private void loginUser(SocialUser socialUser) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(socialUser, null, socialUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
