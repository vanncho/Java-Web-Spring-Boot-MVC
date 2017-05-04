package com.onlinebusreservation.areas.user.entities;

import com.onlinebusreservation.areas.user.entities.enumerations.SocialProvider;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("social_user")
public class SocialUser extends User {

    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    public SocialUser() {
    }

    public SocialProvider getSocialProvider() {
        return socialProvider;
    }

    public void setSocialProvider(SocialProvider socialProvider) {
        this.socialProvider = socialProvider;
    }
}
