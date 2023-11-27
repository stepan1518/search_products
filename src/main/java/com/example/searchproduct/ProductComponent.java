package com.example.searchproduct;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.awt.Desktop;
import java.net.URI;

import com.example.searchproduct.steppp1518.parser.Product;

public class ProductComponent extends AnchorPane {
    @FXML
    private Label _name;
    @FXML
    private Label _procent;
    @FXML
    private Text _price;
    @FXML
    private Text _sale_price;
    private String _url;
    public ProductComponent(Product product) {
        var loader = new FXMLLoader(getClass().getResource("Product.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        updateElement(product);
    }
    @FXML
    private void onSearchProduct() {
        try {
            URI uri = new URI(_url);
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {

        }
    }
    public void updateElement(final Product product) {
        _url = product.url;
        _name.setText(product.name);
        _procent.setText(String.valueOf(product.sale) + "%");
        _price.setText(String.valueOf(product.price) + "р");
        _sale_price.setText(String.valueOf(product.sale_price) + "р");
    }
}
