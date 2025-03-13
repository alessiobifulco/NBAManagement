package controller;

import core.Model;
import view.HistoryPage;

public class HisoryController {

    private final Model model;
    private final HistoryPage historyPage;

    public HisoryController(Model model, HistoryPage historyPage) {
        this.model = model;
        this.historyPage = historyPage;
    }
}
