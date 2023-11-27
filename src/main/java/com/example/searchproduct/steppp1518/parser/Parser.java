package com.example.searchproduct.steppp1518.parser;

import java.util.List;

public interface Parser {
    List<Product> search(final String product, final int page);
}
