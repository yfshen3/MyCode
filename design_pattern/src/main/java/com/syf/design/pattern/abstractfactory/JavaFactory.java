package com.syf.design.pattern.abstractfactory;

public class JavaFactory implements CourseFactory {
    public Video getVideo() {
        return new JavaVideo();
    }

    public Article getArticle() {
        return new JavaArticle();
    }
}
