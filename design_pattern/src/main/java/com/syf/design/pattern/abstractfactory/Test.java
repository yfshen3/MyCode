package com.syf.design.pattern.abstractfactory;

public class Test {

    public static void main(String[] args) {
//        CourseFactory factory = new JavaFactory();
//        Video video = factory.getVideo();
//        Article article = factory.getArticle();
//        video.produce();
//        article.produce();
        String a = "haha123";
        String newA = a.replace("45","67");
        System.out.println(newA);
    }
}
