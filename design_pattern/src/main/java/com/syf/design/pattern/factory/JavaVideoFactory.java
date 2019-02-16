package com.syf.design.pattern.factory;

public class JavaVideoFactory implements VideoFactory {

    public Video getVideo() {
        return new JavaVideo();
    }
}
