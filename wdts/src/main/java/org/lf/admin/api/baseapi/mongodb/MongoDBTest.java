package org.lf.admin.api.baseapi.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MongoDBTest {

    public static void main(String[] args) {
        MongoClient client = new MongoClient("192.168.199.131");
        // 得到要操作的数据库
        MongoDatabase database = client.getDatabase("spitdb");
        // 得到要操作的集合
        MongoCollection<Document> collection = database.getCollection("spit");
//        // 得到集合中所有的文档
//        FindIterable<Document> documents = collection.find();
        // 封装查询条件
        Map<String, Object> map = new HashMap<>();
        map.put("userid","1013");
        BasicDBObject bson = new BasicDBObject(map);
        FindIterable<Document> documents = collection.find(bson);
        // 遍历数据
        documents.forEach((Consumer<? super Document>) document -> {
            System.out.println("内容：" + document.getString("content"));
            System.out.println("用户id：" + document.getString("userid"));
            System.out.println("访问量：" + document.getInteger("visits"));
        });
        client.close();
    }
}
