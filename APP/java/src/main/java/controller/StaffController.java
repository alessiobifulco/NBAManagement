package controller;

import core.Model;
import view.StaffPage;

public class StaffController {

    private final Model model;
    private final StaffPage staffPage;

    public StaffController(Model model, StaffPage staffPage) {
        this.model = model;
        this.staffPage = staffPage;
    }
}
