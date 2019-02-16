package com.syf.design.pattern.simplefactory;

public class VideoFactory {

//    public static Video getVideo(String type) {
//        if ("java".equals(type)) {
//           return new JavaVideo();
//        } else if ("python".equals(type)) {
//            return new PythonVideo();
//        } else {
//            return null;
//        }
//    }

    /**
     * 使用反射
     */
    public static Video getVideo(Class c) {
        Video video = null;
        try {
            video = (Video) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return video;
    }
}
