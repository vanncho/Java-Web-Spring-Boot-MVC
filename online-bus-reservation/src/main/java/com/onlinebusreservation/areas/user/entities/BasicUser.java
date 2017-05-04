package com.onlinebusreservation.areas.user.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("basic_user")
public class BasicUser extends User {
}
