package controller;

import core.Model;
import view.LoginPage;

public class LoginController {
    private final Model model;
    private final LoginPage loginPage;

    public LoginController(Model model, LoginPage loginPage) {
        this.model = model;
        this.loginPage = loginPage;
    }

}
