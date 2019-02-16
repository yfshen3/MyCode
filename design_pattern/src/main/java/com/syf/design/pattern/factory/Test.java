package com.syf.design.pattern.factory;

public class Test {

    public static void main(String[] args) {
        VideoFactory factory = new JavaVideoFactory();
        Video video = factory.getVideo();
        if (video == null) {
            return;
        }
        video.produce();
    }
}
