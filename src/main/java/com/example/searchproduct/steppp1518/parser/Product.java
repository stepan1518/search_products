package com.example.searchproduct.steppp1518.parser;

import org.json.JSONObject;

public class Product implements Comparable<Product> {
    public String image_url;
    public String name;
    public String brand;
    public String url;
    public String id;
    public long price;
    public long sale_price;
    public short sale;
    public double rating;

    public Product(final JSONObject product)
    {
        name = product.get("name").toString();
        price = Long.parseLong(product.get("priceU").toString()) / 100;
        sale_price = Long.parseLong(product.get("salePriceU").toString()) / 100;
        brand = product.get("brand").toString();
        id = product.get("id").toString();
        sale = Short.parseShort(product.get("sale").toString());
        rating = Double.parseDouble(product.get("reviewRating").toString());
    }

    @Override
    public String toString()
    {
        return name + " - " + sale_price + "р" + " *" + rating + "\n";
    }

    @Override
    public int compareTo(Product o) {
        return (int) (sale_price - o.sale_price);
    }
}
