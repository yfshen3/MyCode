package test;

import java.util.TimeZone;

public class NewTest {
    public static void main(String[] args) {
        long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        System.out.println((int)(zero/1000));

        System.out.println(2 > 1L);
    }
}
