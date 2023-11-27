package com.example.searchproduct.steppp1518.parser;

import org.json.JSONObject;

public class Product implements Comparable<Product> {
    final public String name;
    final public String url;
    final public String id;
    final public long price;
    final public long sale_price;
    final public short sale;

    public Product(final JSONObject product, final String Url)
    {
        name = product.get("name").toString();
        price = Long.parseLong(product.get("priceU").toString()) / 100;
        sale_price = Long.parseLong(product.get("salePriceU").toString()) / 100;
        id = product.get("id").toString();
        sale = Short.parseShort(product.get("sale").toString());
        url = Url;
    }
    @Override
    public int compareTo(Product o) {
        return (int) (sale_price - o.sale_price);
    }
}
