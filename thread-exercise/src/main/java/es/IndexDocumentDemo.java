package es;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IndexDocumentDemo {

    public static void main(String[] args) {
        try (RestHighLevelClient client = InitClient.getClient()) {
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("username", "Curry");
            jsonMap.put("postDate", new Date());
            jsonMap.put("message", "trying out Elasticsearch");
            IndexRequest request = new IndexRequest("user").id("1").source(jsonMap);

            //3、其他的一些可选设置
//            request.routing("routing");  //设置routing值
//            request.timeout(TimeValue.timeValueSeconds(1));  //设置主分片等待时长
//            request.setRefreshPolicy("wait_for");  //设置重刷新策略
//            request.version(2);  //设置版本号
//            request.opType(DocWriteRequest.OpType.CREATE);  //操作类别

            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            if (response != null) {
                String index = response.getIndex();
                String id = response.getId();
                long version = response.getVersion();
                if (response.getResult() == DocWriteResponse.Result.CREATED) {
                    System.out.println("新增文档成功");
                    System.out.println("index:" + index);
                    System.out.println("id:" + id);
                    System.out.println("version:" + version);
                } else if (response.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println("修改文档成功");
                }
                // 分片处理信息
                ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

                }
                // 如果有分片副本失败，可以获取失败原因信息
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure :
                            shardInfo.getFailures()) {
                        String reason = failure.reason();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
