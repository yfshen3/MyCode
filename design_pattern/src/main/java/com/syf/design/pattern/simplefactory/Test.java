package com.syf.design.pattern.simplefactory;

public class Test {

    public static void main(String[] args) {
//        Video video = VideoFactory.getVideo("java");
        Video video = VideoFactory.getVideo(JavaVideo.class);
        if (video == null) {
            return;
        }
        video.produce();
    }
}
