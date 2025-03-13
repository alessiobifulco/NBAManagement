package controller;

import core.Model;
import view.TradePage;

public class TradeController {

    private final Model model;
    private final TradePage tradePage;

    public TradeController(Model model, TradePage tradePage) {
        this.model = model;
        this.tradePage = tradePage;
    }
}
