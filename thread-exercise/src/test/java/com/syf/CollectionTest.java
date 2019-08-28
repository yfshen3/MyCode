package com.syf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionTest {
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        List<Product> productList = new ArrayList<>();
        Product product1 = new Product("10005,10007");
        Product product2 = new Product("10007");
        Product product3 = new Product("10006,10007");

        productList.add(product1);
        productList.add(product2);
        productList.add(product3);

        list1.add("10005");
        list1.add("10006");

        list2.add("10005,10007");
        list2.add("10007");
        list2.add("10006,10007");

        List<Product> result = productList.stream()
                .filter(product -> !Collections.disjoint(Arrays.asList(product.getCategoryIds().split(",")), list1))
                .collect(Collectors.toList());

        result.forEach(r -> System.out.println(r.getCategoryIds()));

    }
}

class Product {
    private String categoryIds;

    public Product(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }
}
