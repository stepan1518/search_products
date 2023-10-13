package com.example.searchproduct.steppp1518.parser;

import org.json.JSONObject;

public class Product implements Comparable<Product> {
    public String name;
    public String url;
    public String id;
    public long price;
    public long sale_price;
    public short sale;

    public Product(final JSONObject product)
    {
        name = product.get("name").toString();
        price = Long.parseLong(product.get("priceU").toString()) / 100;
        sale_price = Long.parseLong(product.get("salePriceU").toString()) / 100;
        id = product.get("id").toString();
        sale = Short.parseShort(product.get("sale").toString());
    }
    @Override
    public int compareTo(Product o) {
        return (int) (sale_price - o.sale_price);
    }
}
