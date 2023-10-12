package com.example.searchproduct;

import com.example.searchproduct.steppp1518.parser.Product;
import com.example.searchproduct.steppp1518.parser.WaiborisParser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;

public class HelloController {
    @FXML
    private TextField _input;
    @FXML
    private ListView _items;
    private final WaiborisParser _parser = new WaiborisParser();
    @FXML
    protected void onSearchButtonClick() {
        _items.getItems().clear();

        var text = _input.getText();
        if (text.length() == 0) {return;}
        var products = _parser.search(text, 1);
        if (products == null || products.size() == 0) {return;}

        Collections.sort(products);
        products.forEach(el -> _items.getItems().addAll(new ProductComponent(el)));
    }
}