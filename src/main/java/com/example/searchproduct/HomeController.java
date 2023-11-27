package com.example.searchproduct;

import com.example.searchproduct.steppp1518.parser.ParseRequest;
import com.example.searchproduct.steppp1518.parser.Product;
import com.example.searchproduct.steppp1518.parser.WaiborisParser;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.*;

public class HomeController {
    @FXML
    private TextField _input;
    @FXML
    private ListView _items;
    @FXML
    private Text _page_text;
    private final int _MAX_PAGE_COUNT = 10;
    private final ParseRequest _request = new ParseRequest(WaiborisParser.class, _MAX_PAGE_COUNT);
    private int _page = 1;
    private final int _ELEMENTS_COUNT = 100;
    private int _page_count = 1;
    private List<Product> _products = null;
    private Map<String, List<Product>> _requests = new HashMap();
    @FXML
    protected void onSearchButtonClick() {
        _page = 1;
        var text = _input.getText();
        if (text.length() == 0) {_products = null; updatePage(); return;}
        if ((_products = _requests.get(text)) == null) {
            _products = _request.search(text);
            _requests.put(text, new ArrayList(_products));
        }
        if (_products == null || _products.size() == 0) {updatePage(); return;}
        Collections.sort(_products);
        _page_count = Math.max((int)Math.ceil((double)_products.size() / _ELEMENTS_COUNT), 1);
        updatePage();
    }
    @FXML
    private void thenPage() {
        _page = (_page - 2 + _page_count) % _page_count + 1;
        updatePage();
    }
    @FXML
    private void nextPage() {
        _page = _page % _page_count + 1;
        updatePage();
    }
    private void updatePage() {
        _items.getItems().clear();
        if (_products == null || _products.size() == 0) {
            _page = 1;
            _page_text.setText(String.valueOf(_page));
            return;
        }
        _page_text.setText(String.valueOf(_page));
        for (int i = (_page - 1) * _ELEMENTS_COUNT;
             i < _page * _ELEMENTS_COUNT && i < _products.size();
             i++)
        {
            _items.getItems().add(new ProductComponent(_products.get(i)));
        }
    }
}