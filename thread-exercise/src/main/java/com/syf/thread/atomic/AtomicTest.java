package com.syf.thread.atomic;

import java.util.ArrayList;
import java.util.List;

public class AtomicTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
       if (!list.contains("1")) {
           System.out.println("haha");
       }
    }
}
