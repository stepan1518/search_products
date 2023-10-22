package com.example.searchproduct.steppp1518.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParseRequest {
    private final Class<? extends Parser> _parser;
    private final int _PAGE_COUNT;
    public ParseRequest(final Class<? extends Parser> parser, final int PAGE_COUNT) {
        _parser = parser;
        _PAGE_COUNT = PAGE_COUNT;
    }
    public List<Product> search(final String product) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        var executor = Executors.newFixedThreadPool(numThreads);

        List<Product> resultList = new ArrayList<>();

        List<Callable<List<Product>>> callables = new ArrayList<>();

        for (int i = 0; i < _PAGE_COUNT; i++) {
            final int currPage = i + 1;
            callables.add(() -> {
                List<Product> result = null;
                try {
                    var inst = _parser.newInstance();
                    result = inst.search(product, currPage);
                } catch (InstantiationException e) {
                    executor.shutdown();
                    return null;
                } catch (IllegalAccessException e) {
                    executor.shutdown();
                    return null;
                }
                return result;
            });
        }

        List<Future<List<Product>>> futures = null;
        try {
            futures = executor.invokeAll(callables);
        } catch (InterruptedException e) {
            executor.shutdown();
            return null;
        }

        for (var future : futures) {
            try {
                var result = future.get();
                resultList.addAll(result);
            } catch (Exception e) {

            }
        }

        executor.shutdown();
        return resultList;
    }
}
