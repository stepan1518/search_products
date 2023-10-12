package com.example.searchproduct.steppp1518.parser;

import java.io.IOException;
import java.util.ArrayList;

public class PricesRequest implements Runnable {
    private final Parser _parser;
    private final String _product;
    private final int _page;
    public PricesRequest(Parser parser, String product, int page) {
        _parser = parser;
        _product = product;
        _page = page;
    }

    @Override
    public void run() {
        //var products = _parser.search(_product, _page);
    }
}
