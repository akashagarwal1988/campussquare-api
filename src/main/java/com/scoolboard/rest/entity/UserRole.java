package com.scoolboard.rest.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by akasha on 9/18/15.
 */
public class UserRole {

    @Getter
    @Setter
    private String email;


        @Getter
        @Setter
        private String role;

        UserRole(String email, String role) {
            this.email = email;
            this.role = role;
        }
    UserRole(){
        this.email ="";
        this.role = "ADMIN_ROLE";
    }


}
