package com.syf.design.pattern.factory;

public class PythonVideoFactory implements VideoFactory {

    public Video getVideo() {
        return new PythonVideo();
    }
}
