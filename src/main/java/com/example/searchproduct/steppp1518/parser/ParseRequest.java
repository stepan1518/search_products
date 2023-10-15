package com.example.searchproduct.steppp1518.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ParseRequest {
    private final Class<? extends Parser> _parser;
    private final int _PAGE_COUNT;
    public ParseRequest(final Class<? extends Parser> parser, final int PAGE_COUNT) {
        _parser = parser;
        _PAGE_COUNT = PAGE_COUNT;
    }
    public List<Product> search(final String product) {
        List<Product> products = Collections.synchronizedList(new ArrayList<Product>());
        var parsers = new ArrayList<Parser>();

        for (int i = 0; i < _PAGE_COUNT; i++) {
            try {
                parsers.add(_parser.newInstance());
            } catch (InstantiationException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            }
        }

        var page = new AtomicInteger(1);
        var isInterrupt = new AtomicBoolean(false);

        parsers.parallelStream().forEach(parser -> {
            try {
                if (isInterrupt.get()) Thread.currentThread().interrupt();
                var data = parser.search(product, page.getAndIncrement());
                if (data == null || data.size() == 0) {
                    isInterrupt.set(true);
                    Thread.currentThread().interrupt();
                }
                products.addAll(data);
            } catch (IOException e) {

            }
        });

        return products;
    }
}
