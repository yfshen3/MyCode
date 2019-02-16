package com.syf.design.pattern.abstractfactory;

public class PythonFactory implements CourseFactory {
    public Video getVideo() {
        return new PythonVideo();
    }

    public Article getArticle() {
        return new PythonArticle();
    }
}
