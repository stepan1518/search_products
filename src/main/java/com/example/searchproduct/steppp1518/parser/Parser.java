package com.example.searchproduct.steppp1518.parser;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;

public interface Parser {
    ArrayList<Product> search(final String product, final int page) throws IOException;
}
