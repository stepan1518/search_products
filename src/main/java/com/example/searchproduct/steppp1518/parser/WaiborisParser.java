package com.example.searchproduct.steppp1518.parser;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class WaiborisParser implements Parser {
    private static final String _URL = "https://search.wb.ru/exactmatch/ru/common/v4/" +
            "search?TestGroup=freq_02&" +
            "TestID=286&appType=1&curr=rub&dest=-1257786" +
            "&page=$NAME$&query=$NAME$&regions=80,38,83,4,64,33,68,70,30,40,86,75,69,1,31,66,110,48,22,71,114" +
            "&resultset=catalog&sort=popular&spp=31&suppressSpellcheck=false";

    private static final String _IMAGE_URL = "https://basket-12.wb.ru/" +
            "vol$NAME$/part$NAME$/$NAME$/images/big/1.webp";

    private static final String _PRODUCT_URL = "https://www.wildberries.ru/catalog/" +
            "$NAME$/detail.aspx";

    private static final String _REPLACE_ELEMENT = "$NAME$";

    private static final String NOT_FOUND = "{}";

    private String convertProductUrl(final String id) {
        int start = _PRODUCT_URL.indexOf(_REPLACE_ELEMENT);
        int end = start + _REPLACE_ELEMENT.length();
        return new StringBuilder(_PRODUCT_URL).replace(start, end, id).toString();
    }

    private String convertUrl(final String product, final int page) {
        int start = _URL.indexOf(_REPLACE_ELEMENT);
        int end = start + _REPLACE_ELEMENT.length();
        var result = new StringBuilder(_URL).replace(start, end, String.valueOf(page));
        start = result.indexOf(_REPLACE_ELEMENT);
        end = start + _REPLACE_ELEMENT.length();
        result.replace(start, end, product);
        return result.toString();
    }

    private String convertImageUrl(final String id) {
        var result = new StringBuilder(_IMAGE_URL);
        int start = _IMAGE_URL.indexOf(_REPLACE_ELEMENT);
        result = result.replace(start, _REPLACE_ELEMENT.length() + start, id.substring(0, 4));
        start = result.indexOf(_REPLACE_ELEMENT);
        result = result.replace(start, _REPLACE_ELEMENT.length() + start, id.substring(0, 6));
        start = result.indexOf(_REPLACE_ELEMENT);
        result = result.replace(start, _REPLACE_ELEMENT.length() + start, id);
        return result.toString();
    }

    private ArrayList<Product> parseJson(final String text) {
        var list_products = new ArrayList<Product>();

        var products = new JSONObject(text).getJSONObject("data").getJSONArray("products");
        for (var obj : products) {
            var data = new JSONObject(obj.toString());
            var product = new Product(data);
            product.image_url = convertImageUrl(product.id);
            product.url = convertProductUrl(product.id);
            list_products.add(product);
        }
        return list_products;
    }

    @Override
    public ArrayList<Product> search(final String product, final int page) {
        URL obj = null;
        try {
            obj = new URL(convertUrl(product, page));
        } catch (MalformedURLException e) {
            return null;
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) obj.openConnection();
        } catch (IOException e) {
            return null;
        }
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            return null;
        }
        int responseCode = 0;
        try {
            responseCode = con.getResponseCode();
        } catch (IOException e) {
            return null;
        }
        if (responseCode != HttpURLConnection.HTTP_OK) return null;

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } catch (IOException e) {
            return null;
        }
        String inputLine;
        StringBuffer response = new StringBuffer();

        while (true) {
            try {
                if (!((inputLine = in.readLine()) != null)) break;
            } catch (IOException e) {
                return null;
            }
            response.append(inputLine);
        }

        try {
            in.close();
        } catch (IOException e) {}

        if (NOT_FOUND.equals(response.toString())) return new ArrayList<Product>();

        var products = parseJson(response.toString());
        return products;
    }
}
