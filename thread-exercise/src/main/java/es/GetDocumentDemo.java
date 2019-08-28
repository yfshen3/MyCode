package es;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.util.Map;

public class GetDocumentDemo {
    public static void main(String[] args) {

        try (RestHighLevelClient client = InitClient.getClient()) {
            GetRequest request = new GetRequest("user", "1");

            request.fetchSourceContext(FetchSourceContext.FETCH_SOURCE);

            String[] includes = new String[]{"message", "*Date"};
            String[] excludes = Strings.EMPTY_ARRAY;
            FetchSourceContext fetchSourceContext =
                    new FetchSourceContext(true, includes, excludes);
            request.fetchSourceContext(fetchSourceContext);

//            request.storedFields("message");
//            GetResponse response = client.get(request, RequestOptions.DEFAULT);
//            String message = response.getField("message").getValue();

            GetResponse response = null;
            try {
                response = client.get(request, RequestOptions.DEFAULT);
            } catch (ElasticsearchException e) {
                if (e.status() == RestStatus.NOT_FOUND) {
                    System.out.println("文档没有找到");
                }
                if (e.status() == RestStatus.CONFLICT) {
                    System.out.println("获取时版本冲突，请在此写冲突处理逻辑");
                }
                System.out.println("获取文档异常");
            }

            // 处理响应
            if (response != null) {
                String index = response.getIndex();
                String id = response.getId();
                if (response.isExists()) {
                    long version = response.getVersion();
                    String sourceAsString = response.getSourceAsString();
                    Map<String, Object> sourceAsMap = response.getSourceAsMap();
                    byte[] sourceAsBytes = response.getSourceAsBytes();

                    System.out.println("index:" + index);
                    System.out.println("id:" + id);
                    System.out.println("version:" + version);
                    System.out.println("source:" + sourceAsString);
                } else {
                    System.out.println("文档不存在");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
