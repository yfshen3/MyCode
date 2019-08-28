package com.syf;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EsDemo {

    @Test
    public void test1() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(-1);
        list.add(-2);
        list.stream().filter(integer -> {
            int i = integer;
            return i < 0;
        }).collect(Collectors.toList()).forEach(integer -> integer++);

        list.forEach(integer -> System.out.println(integer));
    }
}
