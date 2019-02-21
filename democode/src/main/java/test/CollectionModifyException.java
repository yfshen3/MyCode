package test;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyException {
    public static void main(String[] args) {
//        List<Integer> users = new ArrayList();
        List<String> users = new CopyOnWriteArrayList<>();
        users.add("张三");
        users.add("李四");
        users.add("王五");
        Iterator iterator = users.iterator();
        while (iterator.hasNext()) {
            String user = (String) iterator.next();
            if ("王五".equals(user)) {
                users.remove(user);
            } else {
                System.out.println(user);
            }
        }
    }
}
