package com.syf.design.pattern.simplefactory;

public class PythonVideo extends Video {

    @Override
    public void produce() {
        System.out.println("录制Python视频课程");
    }
}