package com.syf.design.pattern.abstractfactory;

public class Test {

    public static void main(String[] args) {
        CourseFactory factory = new JavaFactory();
        Video video = factory.getVideo();
        Article article = factory.getArticle();
        video.produce();
        article.produce();
    }
}
