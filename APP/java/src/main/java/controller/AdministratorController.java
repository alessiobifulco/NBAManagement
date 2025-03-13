package controller;

import core.Model;
import view.AdministratorPage;

public class AdministratorController {

    private final Model model;
    private final AdministratorPage administratorPage;

    public AdministratorController(Model model, AdministratorPage administratorPage) {
        this.model = model;
        this.administratorPage = administratorPage;
    }

}
