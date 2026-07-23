package com.ddnik.controller;

import com.ddnik.AuthorizedUser;
import com.ddnik.Main;
import com.ddnik.Menu;

public class UserController {

    private AuthorizedUser user;

    public UserController(AuthorizedUser user) {
        this.user = user;
    }

    public void start() {
        Menu.cls();
        System.out.println("UserController start");
    }

}
