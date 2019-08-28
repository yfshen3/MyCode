package es;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import java.util.HashMap;
import java.util.Map;

public class CreateIndexDemo {
    public static void main(String[] args) {

        try (RestHighLevelClient client = InitClient.getClient()) {

            // 1.创建索引名
            CreateIndexRequest request = new CreateIndexRequest("book");

            // 2.索引setting配置
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 2));

            // 3.设置索引的mapping
//            方式1:
//            request.mapping("_doc",
//                    "{\n" +
//                            "  \"properties\": {\n" +
//                            "    \"message\": {\n" +
//                            "      \"type\": \"text\"\n" +
//                            "    }\n" +
//                            "  }\n" +
//                            "}",
//                    XContentType.JSON);

//            方式2:
            Map<String, Object> message = new HashMap<>();
            message.put("type", "text");
            Map<String, Object> properties = new HashMap<>();
            properties.put("message", message);
            Map<String, Object> mapping = new HashMap<>();
            mapping.put("properties", properties);
            request.mapping("_doc",mapping);

//            4.设置索引别名
//            request.alias(new Alias("book_alias"));

            // 5.发送请求
            // 5.1同步方式
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

            //处理响应
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();

            System.out.println("请求结果---------------");
            System.out.println("acknowledged:"+acknowledged);
            System.out.println("shardsAcknowledged:"+shardsAcknowledged);

            // 5.2异步方式发送请求
//            ActionListener<CreateIndexResponse> listener = new ActionListener<CreateIndexResponse>() {
//                @Override
//                public void onResponse(CreateIndexResponse response) {
//                    boolean acknowledged = response.isAcknowledged();
//                    boolean shardsAcknowledged = response.isShardsAcknowledged();
//
//                    System.out.println("请求结果---------------");
//                    System.out.println("acknowledged:"+acknowledged);
//                    System.out.println("shardsAcknowledged:"+shardsAcknowledged);
//                }
//
//                @Override
//                public void onFailure(Exception e) {
//                     e.printStackTrace();
//                }
//            };
//            client.indices().createAsync(request, RequestOptions.DEFAULT, listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
