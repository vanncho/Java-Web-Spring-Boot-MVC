package com.onlinebusreservation.areas.user.controllers;

import com.onlinebusreservation.areas.user.services.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FacebookLoginController {

    private final SocialUserService socialUserService;

    private Facebook facebook;

    private ConnectionRepository connectionRepository;

    @Autowired
    public FacebookLoginController(SocialUserService socialUserService,
                                   Facebook facebook,
                                   ConnectionRepository connectionRepository) {
        this.socialUserService = socialUserService;
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping("/user/register/facebook")
    public String getRegisterOrLogin() {

        if (this.connectionRepository.findPrimaryConnection(Facebook.class) == null) {

            return "redirect:/connect/facebook";
        }

        ConnectionKey connectionKey = this.connectionRepository.findPrimaryConnection(Facebook.class).getKey();

        String userKey = this.connectionRepository.findPrimaryConnection(Facebook.class).getKey().getProviderUserId();
        String[] fields = {"id", "email"};

        User facebook = this.facebook.fetchObject(userKey, User.class, fields);

        this.socialUserService.registerOrLogin(facebook);
        this.connectionRepository.removeConnection(connectionKey);

        return "redirect:/";
    }
}
